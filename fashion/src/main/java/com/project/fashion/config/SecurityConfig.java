package com.project.fashion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable()) // Nếu không muốn dùng CSRF
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/user/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll() // Cho phép tất cả truy cập vào "/user/**"
                        .anyRequest().permitAll() // Các request khác yêu cầu xác thực
                )
                .httpBasic(Customizer.withDefaults()); // Sử dụng phương thức mới để cấu hình HTTP Basic

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Bean để mã hóa mật khẩu
    }
}
