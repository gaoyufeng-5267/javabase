package jp.co.tlzs.weget.security.auth;

import jp.co.tlzs.weget.redis.repository.AuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

public class ReactiveTokenAuthenticationManager implements ReactiveAuthenticationManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ResourceServerTokenServices tokenServices;
    private final AuthRepository authRepository;
    private final Duration loginTimeout;

    public ReactiveTokenAuthenticationManager(ResourceServerTokenServices tokenServices, AuthRepository authRepository) {
        this.tokenServices = tokenServices;
        this.authRepository = authRepository;
        this.loginTimeout = Duration.parse("PT12H");
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        return Mono.defer(() -> {
            String accessToken = (String) authentication.getPrincipal();
            String userAgent = (String) authentication.getCredentials();

            OAuth2Authentication loadAuthentication = tokenServices.loadAuthentication(accessToken);
            // tokenServicesを拡張することで、loadAuthentication内でjtiを設定済み
            String jti = (String) loadAuthentication.getDetails();
            return authRepository.findById(jti)
                    // tokenの有効期限とuserAgentを確認
                    .switchIfEmpty(Mono.defer(() -> {
                        log.warn("expired token. {}", jti);
                        return Mono.error(new CredentialsExpiredException("Invalid token (token expired)"));
                    }))
                    .filter(auth -> {
                        if (Objects.equals(auth.getAgent(), userAgent)) {
                            // userAgent確認後、2段階認証状態を確認。
                            return auth.isStatus();
                        }
                        log.warn("userAgent not match {}, {}", auth.getAgent(), userAgent);
                        throw new InvalidTokenException("Invalid token (userAgent not match) :" + userAgent);
                    })
                    // 2段階認証済みであれば、token期限を延長して、ログイン状態を返却。
                    .flatMap(auth -> authRepository.expire(auth.getId(), loginTimeout).thenReturn(loadAuthentication))
                    .cast(Authentication.class)
                    .switchIfEmpty(Mono.defer(() -> {
                        // 2段階認証用のユーザに差し替える。
                        AnonymousAuthenticationToken anonymousAuth = new AnonymousAuthenticationToken(
                                "key", loadAuthentication.getUserAuthentication(), loadAuthentication.getAuthorities());
                        anonymousAuth.setDetails(jti);
                        return Mono.just(anonymousAuth);
                    }))
                    // ログアウト用
                    .onErrorResume(AuthenticationException.class, e -> Mono.just(loadAuthentication));
        });
    }
}
