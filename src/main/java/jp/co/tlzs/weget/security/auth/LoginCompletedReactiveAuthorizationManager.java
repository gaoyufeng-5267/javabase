package jp.co.tlzs.weget.security.auth;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import reactor.core.publisher.Mono;

public class LoginCompletedReactiveAuthorizationManager<T> implements ReactiveAuthorizationManager<T> {

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, T object) {
        return authentication
                .map(a -> new AuthorizationDecision(a.isAuthenticated() && a instanceof OAuth2Authentication))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

    /**
     * Gets an instance of {@link LoginCompletedReactiveAuthorizationManager}
     * @param <T>
     * @return
     */
    public static <T> LoginCompletedReactiveAuthorizationManager<T> loginCompleted() {
        return new LoginCompletedReactiveAuthorizationManager<>();
    }

    private LoginCompletedReactiveAuthorizationManager() {}
}
