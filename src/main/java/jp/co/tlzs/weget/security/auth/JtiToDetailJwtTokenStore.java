package jp.co.tlzs.weget.security.auth;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

public class JtiToDetailJwtTokenStore extends JwtTokenStore {

    public JtiToDetailJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token){
        OAuth2Authentication oAuth2Authentication = super.readAuthentication(token);
        oAuth2Authentication.setDetails(token.getAdditionalInformation().get(AccessTokenConverter.JTI));
        return oAuth2Authentication;
    }
}
