package com.bordserver.jwy.config;

import com.bordserver.jwy.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity  // 스프링 필터 체인에 스프링 시큐리티 필터 등록
@EnableMethodSecurity // 메소드 단위 권한 설정 활성화
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberDao memberDao;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SessionProvider sessionProvider(PasswordEncoder passwordEncoder) {
        return new SessionProvider(memberDao, passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity builder, AuthenticationManager authenticationManager)
            throws Exception {
        AntPathRequestMatcher[] apiWhitelist = new AntPathRequestMatcher[]{
                new AntPathRequestMatcher("/users/sign-up"),
                new AntPathRequestMatcher("/users/sign-in")
        };

        AntPathRequestMatcher[] adminList = new AntPathRequestMatcher[]{
                new AntPathRequestMatcher("/categories/**")
        };
        builder.authorizeHttpRequests(authorizeHttpRequests ->
                authorizeHttpRequests
                        .requestMatchers(apiWhitelist).permitAll()
                        .requestMatchers(adminList).hasAuthority("ADMIN")
                        .anyRequest().authenticated());

        builder.csrf(AbstractHttpConfigurer::disable);
        builder.formLogin(AbstractHttpConfigurer::disable);
        builder.addFilterBefore(new SessionFilter(authenticationManager, sessionProvider(passwordEncoder())),
                BasicAuthenticationFilter.class);

        return builder.build();
    }
}
