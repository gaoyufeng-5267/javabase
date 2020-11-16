package jp.co.tlzs.weget.security.config;

import jp.co.tlzs.weget.redis.repository.AuthRepository;
import jp.co.tlzs.weget.security.auth.JtiToDetailJwtTokenStore;
import jp.co.tlzs.weget.security.auth.ReactiveTokenAuthenticationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.springframework.security.config.web.server.ServerHttpSecurity.http;

@Configuration
public class ResourceServerTokenServicesConfiguration {

    private static final int RETRY_WAIT = 10;
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    SecurityWebFilterChain springSecurityFilterChain(ReactiveAuthenticationManager authenticationManager) {
        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(authenticationManager);
        authenticationWebFilter.setAuthenticationConverter(serverWebExchange -> {
            String token = findToken(serverWebExchange.getRequest());
            String userAgent = getUserAgent(serverWebExchange.getRequest());
            return token == null ? Mono.empty() : Mono.just(new PreAuthenticatedAuthenticationToken(token, userAgent));
        });

        return http()
                .csrf().disable()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange()
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()
                    .pathMatchers("/keepalive/**").permitAll()
                    .pathMatchers("/api/products1/**").permitAll()
                    .pathMatchers("/api/products/**").authenticated()
                    .anyExchange().access((authentication, object) ->
                        authentication
                                .map(auth -> new AuthorizationDecision(auth.isAuthenticated() && auth instanceof OAuth2Authentication))
                                .defaultIfEmpty(new AuthorizationDecision(false))
                    )
                .and().build();
    }

    private String findToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        for (String value : headers.getValuesAsList(HttpHeaders.AUTHORIZATION)) {
            if ((value.toLowerCase().startsWith(OAuth2AccessToken.BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(OAuth2AccessToken.BEARER_TYPE.length()).trim();
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }
        for (String value : headers.getValuesAsList("Sec-WebSocket-Protocol")) {
            if ("JWT".equals(value)) {
                continue;
            }
            return value;
        }
        return request.getQueryParams().getFirst("auth");
    }

    private String getUserAgent(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        return headers.getFirst(HttpHeaders.USER_AGENT);
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ResourceServerTokenServices tokenServices, AuthRepository authRepository) {
        return new ReactiveTokenAuthenticationManager(tokenServices, authRepository);
    }
    @Configuration
    @EnableResourceServer
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    static class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                        .configurationSource(corsConfigurationSource())
                    .and()
                    .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS).permitAll()
                        .antMatchers("/keepalive/**").permitAll()
                        .antMatchers("/oauth/token", "/oauth/token_key", "/sign_up").permitAll()
                        .antMatchers("/activate", "/signup", "/password/reset", "/trade/refresh").permitAll()
                        .antMatchers(HttpMethod.POST,"/login").permitAll()
                        .antMatchers(HttpMethod.PATCH,"/login").anonymous()
                        .anyRequest().authenticated()
                    .and()
                    .logout()
                        .disable()
            ;
        }

        private CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowCredentials(true);
            config.applyPermitDefaultValues();
            config.addAllowedOrigin(CorsConfiguration.ALL);
            config.addAllowedMethod(CorsConfiguration.ALL);

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }
    }

    @Bean
    public AuthenticationManager tokenAuthenticationManager(ReactiveAuthenticationManager reactiveAuthenticationManager) {
        return authentication -> reactiveAuthenticationManager.authenticate(authentication).block();
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultTokenServices jwtTokenServices(JwtAccessTokenConverter jwtTokenEnhancer) {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setTokenStore(new JtiToDetailJwtTokenStore(jwtTokenEnhancer));
        return services;
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAccessTokenConverter jwtTokenEnhancer(@Value("${security.jwt.key-uri:}") String keyUri,
                                                    @Value("${security.jwt.key-value:}") String keyValue) {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        if (!StringUtils.hasText(keyValue)) {
            keyValue = getKeyFromServer(keyUri);
        }
        if (StringUtils.hasText(keyValue) && !keyValue.startsWith("-----BEGIN")) {
            converter.setSigningKey(keyValue);
        }
        if (keyValue != null) {
            converter.setVerifierKey(keyValue);
        }
        return converter;
    }

    private String getKeyFromServer(String url) {
        if (url.isEmpty()) {
            return null;
        }
        RestTemplate keyUriRestTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
//        String username = this.resource.getClientId();
//        String password = this.resource.getClientSecret();
//        if (username != null && password != null) {
//            byte[] token = Base64.getEncoder()
//                    .encode((username + ":" + password).getBytes());
//            headers.add("Authorization", "Basic " + new String(token));
//        }
        HttpEntity<Void> request = new HttpEntity<>(headers);
        String value;
        try {
            value = (String) keyUriRestTemplate
                    .exchange(url, HttpMethod.GET, request, Map.class).getBody()
                    .get("value");
        } catch (RestClientException e) {
            log.warn(e.getMessage());
            try {
                log.info("retrying after {} seconds.", RETRY_WAIT);
                TimeUnit.SECONDS.sleep(RETRY_WAIT);
            } catch (InterruptedException ignored) {
            }
            value = (String) keyUriRestTemplate
                    .exchange(url, HttpMethod.GET, request, Map.class).getBody()
                    .get("value");
        }
        return value;
    }
}
