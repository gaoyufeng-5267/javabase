package jp.co.tlzs.weget.security.auth;

import jp.co.tlzs.weget.redis.model.Auth;
import jp.co.tlzs.weget.redis.repository.AuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.time.Duration;
import java.util.Map;

/**
 *
 */
public class TokenAuthenticationManager implements AuthenticationManager {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ResourceServerTokenServices tokenServices;
    private final AuthRepository authRepository;
    private final Duration loginTimeout;

    public TokenAuthenticationManager(ResourceServerTokenServices tokenServices, AuthRepository authRepository) {
        this.tokenServices = tokenServices;
        this.authRepository = authRepository;
        this.loginTimeout = Duration.parse("PT12H");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication == null) {
            throw new InvalidTokenException("Invalid token (token not found)");
        }
        String token = (String) authentication.getPrincipal();
        OAuth2Authentication auth = tokenServices.loadAuthentication(token);
        if (auth == null) {
            throw new InvalidTokenException("Invalid token: " + token);
        }
        // tokenの有効期限とuserAgentを確認
        Map<String, Object> information = tokenServices.readAccessToken(token).getAdditionalInformation();
        String jti = (String) information.get(AccessTokenConverter.JTI);
        String userAgent = (String) authentication.getCredentials();

        // 各endpointで使用可能JTI設定
        auth.setDetails(jti);

        Auth authUser;
        try {
            authUser = authRepository.findById(jti).blockOptional().orElseThrow(() -> {
                throw new InvalidTokenException("Invalid token (token expired)");
            });
        } catch(InvalidTokenException e) {
            log.warn("expired token. {}", e.getStackTrace());
            return auth;
        }
        if (!authUser.getAgent().equals(userAgent)) {
            log.warn("userAgent not match {}, {}", authUser.getAgent(), userAgent);
            throw new InvalidTokenException("Invalid token (userAgent not match) :" + userAgent);
        }
        if (!authUser.isStatus()) {
            AnonymousAuthenticationToken anonymousAuth = new AnonymousAuthenticationToken(
                    "key", auth.getUserAuthentication(), auth.getUserAuthentication().getAuthorities());
            anonymousAuth.setDetails(jti);
            return anonymousAuth;
        }
        authRepository.save(authUser, loginTimeout);
        return auth;
    }
}
