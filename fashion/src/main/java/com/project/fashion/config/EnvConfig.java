
package com.project.fashion.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load(); // Khởi tạo và trả về đối tượng Dotenv
    }
}
