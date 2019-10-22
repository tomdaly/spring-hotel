package com.tomdaly.hotel.business.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/**")
        .authorizeRequests()
        .antMatchers("/", "/login**", "/webjars/**", "/error**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .logout()
        .logoutSuccessUrl("/")
        .permitAll()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/api/**/*");
  }

  @RequestMapping(value = "/user")
  public Principal user(Principal principal) {
    return principal;
  }
}
