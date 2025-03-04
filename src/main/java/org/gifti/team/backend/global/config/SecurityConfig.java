package org.gifti.team.backend.global.config;

import org.gifti.team.backend.auth.type.Role;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors((AbstractHttpConfigurer::disable))
                .csrf((AbstractHttpConfigurer::disable))
                .httpBasic((AbstractHttpConfigurer::disable))
                .formLogin((AbstractHttpConfigurer::disable))
                .formLogin((AbstractHttpConfigurer::disable))
                .headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
                .authorizeHttpRequests((authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(PathRequest.toH2Console())
                        .permitAll()
                        .requestMatchers("/**")
                        .hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
        );
        return httpSecurity.build();
    }
}
