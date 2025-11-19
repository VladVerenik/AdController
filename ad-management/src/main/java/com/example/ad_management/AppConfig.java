package com.example.ad_management;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class AppConfig {

    @Bean
    public Tika tika() {
        return new Tika();
    }
}
