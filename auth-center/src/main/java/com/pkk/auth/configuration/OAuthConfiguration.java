package com.pkk.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @description: oauth的核心类
 * @author: peikunkun
 * @create: 2019-03-27 14:28
 **/
@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients
        .inMemory()
        .withClient("api-gateway")
        .secret("secret")
        .scopes("WRIGTH", "read").autoApprove(true)
        .authorities("WRIGTH_READ", "WRIGTH_WRITE")
        .authorizedGrantTypes("implicit", "refresh_token", "password", "authorization_code");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    endpoints
        .tokenStore(jwtTokenStore())
        .tokenEnhancer(jwtTokenConverter())
        .authenticationManager(authenticationManager);
  }

  @Bean
  public TokenStore jwtTokenStore() {
    return new JwtTokenStore(jwtTokenConverter());
  }

  @Bean
  protected JwtAccessTokenConverter jwtTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("springcloud123");
    return converter;
  }

}
