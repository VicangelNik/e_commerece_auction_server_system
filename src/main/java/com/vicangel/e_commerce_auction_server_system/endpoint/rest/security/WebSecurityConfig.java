package com.vicangel.e_commerce_auction_server_system.endpoint.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
class WebSecurityConfig {

  private final JwtFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(@Nonnull final HttpSecurity http) throws Exception {
    http
      .cors(withDefaults())
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(req ->
                               req.requestMatchers(
                                   "/auth/**",
                                   "/v2/api-docs",
                                   "/v3/api-docs",
                                   "/v3/api-docs/**",
                                   "/swagger-resources",
                                   "/swagger-resources/**",
                                   "/configuration/ui",
                                   "/configuration/security",
                                   "/swagger-ui/**",
                                   "/webjars/**",
                                   "/swagger-ui.html"
                                 )
                                 .permitAll()
                                 .anyRequest()
                                 .authenticated()
      )
      .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
