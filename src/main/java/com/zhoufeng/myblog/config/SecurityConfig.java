package com.zhoufeng.myblog.config;

import com.zhoufeng.myblog.service.Impl.UserDetailsServiceImpl;
import com.zhoufeng.myblog.utils.EncryptUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/static/**",
                        "/css/**",
                        "/js/**",
                        "/img/**",
                        "/font/**",
                        "/lib/**",
                        "/",
                        "/category",
                        "/archive",
                        "/comment",
                        "/about",
                        "/p/**",
                        "/article-tag/**",
                        "/article-category/**",
                        "/admin/login",
                        "/admin/register",
                        "/admin/forget",
                        "/user/login",
                        "/user/register",
                        "/user/username/**",
                        "/user/email/**",
                        "/user/current",
                        "/error"
                ).permitAll()
                .antMatchers(HttpMethod.GET, "/articles/**", "/categories/**", "/tags/**", "/comments/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .logout()
                .logoutUrl("/user/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    request.getSession().invalidate();
                    response.setStatus(HttpServletResponse.SC_OK);
                })
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    String accept = request.getHeader("Accept");
                    if (accept != null && accept.contains("application/json")) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    } else {
                        response.sendRedirect("/admin/login");
                    }
                });
        http.userDetailsService(userDetailsService);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return EncryptUtils.md5Encode(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }
}

