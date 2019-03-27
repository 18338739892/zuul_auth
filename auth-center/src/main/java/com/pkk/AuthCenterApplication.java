package com.pkk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * https://blog.csdn.net/weixin_38003389/article/details/83654721
 *
 * @description: 认证中心的启动类
 * @author: peikunkun
 * @create: 2019-03-27 14:26
 **/
//auth-server的配置类，编写认证授权服务器适配器OAuthConfiguration类，这里主要指定类客户端ID、密钥，以及权限定义与作用范围的声明，指定类TokenStore为JWT
@SpringBootApplication
@EnableDiscoveryClient
public class AuthCenterApplication extends WebSecurityConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(AuthCenterApplication.class, args);
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * auth-server的启动类，这里声明类admin有读写权限，guest只有读的权限；<br/> passwordEncoder()方法用于声明用户名和密码的加密方式，注：只有Spring Security5.0以后才有。
   *
   * @param auth
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .inMemoryAuthentication()
        .withUser("guest").password("guest").authorities("WRIGTH_READ")
        .and()
        .withUser("admin").password("admin").authorities("WRIGTH_READ", "WRIGTH_WRITE");
  }

  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }

}
