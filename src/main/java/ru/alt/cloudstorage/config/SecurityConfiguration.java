package ru.alt.cloudstorage.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.alt.cloudstorage.service.JwtFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers("/login", "/logout").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/list").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/task").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/description").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/priority").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/executor").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/statusauthor").permitAll()
                                .requestMatchers(HttpMethod.OPTIONS, "/statusexecutor").permitAll()
                                .anyRequest().authenticated()
                                .and()
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                ).build();
    }
}
