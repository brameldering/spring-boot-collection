package com.packt.footballui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  public DefaultSecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
    successHandler.setDefaultTargetUrl("/"); // Redirect to the root URL after successful login

    http
        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
            .requestMatchers("/").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauth2 -> oauth2.successHandler(successHandler)); // Add success handler for OAuth2 login


    return http.build();
  }
}
