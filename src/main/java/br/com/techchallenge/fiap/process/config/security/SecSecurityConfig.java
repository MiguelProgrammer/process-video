/*
 * Copyright (c) 2025. MiguelProgrammer
 */

package br.com.techchallenge.fiap.process.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        User.UserBuilder usuario = User.builder();
        UserDetails user = usuario.username("miguelprogrammer")
                .password(passwordEncoder.encode("techchallenge5"))
                .roles()
                .build();
        return new InMemoryUserDetailsManager(user);
    }




    @Bean
    public SecurityFilterChain securedFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                    request.requestMatchers("/process/video/")
                            .permitAll()
                            .requestMatchers("/process/**")
                            .authenticated())
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

