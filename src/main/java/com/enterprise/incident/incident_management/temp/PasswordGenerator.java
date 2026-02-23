package com.enterprise.incident.incident_management.temp;



import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordGenerator {

    @Bean
    CommandLineRunner generatePassword(PasswordEncoder encoder) {
        return args -> {
            System.out.println("BCrypt Password: " + encoder.encode("password123"));
        };
    }
}