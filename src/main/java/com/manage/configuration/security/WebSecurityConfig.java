package com.manage.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    private JWTEntryPoint jwtEntryPoint;
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(JWTEntryPoint jwtEntryPoint,JWTAuthenticationFilter jwtAuthenticationFilter){
        this.jwtEntryPoint = jwtEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception   {
        return http
                .authorizeHttpRequests(configurer -> {
                    configurer.requestMatchers("/api/auth/login").permitAll();
                    configurer.requestMatchers("/api/auth/register").permitAll();
                    configurer.anyRequest().authenticated();
                })
                .sessionManagement(sessionConfigurer ->
                        sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(basic->basic.authenticationEntryPoint(jwtEntryPoint))
                .exceptionHandling(Customizer.withDefaults())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // checks jwt filter first !
                .csrf(csrf->csrf.disable())
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }



    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}





}
