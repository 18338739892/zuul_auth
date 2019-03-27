package com.pkk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @description: 后台服务
 * @author: peikunkun
 * @create: 2019-03-27 14:34
 **/
//要想将一个微服务注册到Eureka Server（或其他服务发现组件，例如Zookeeper、Consul等），Eureka 2.0闭源之后，Consul慢慢会成为主流。
@EnableDiscoveryClient
@SpringBootApplication
//资源服务器通过 @EnableResourceServer 注解来开启一个 OAuth2AuthenticationProcessingFilter 类型的过滤器
@EnableResourceServer
public class BackendServiceApplication extends ResourceServerConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(BackendServiceApplication.class, args);
  }


  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/**").authenticated()
        .antMatchers(HttpMethod.GET, "/test")
        .hasAuthority("WRIGTH_READ");
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources
        .resourceId("WRIGTH")
        .tokenStore(jwtTokenStore());
  }

  @Bean
  protected JwtAccessTokenConverter jwtTokenConverter() {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    converter.setSigningKey("springcloud123");
    return converter;
  }

  @Bean
  public TokenStore jwtTokenStore() {
    return new JwtTokenStore(jwtTokenConverter());
  }
}
