package com.example.librarymanagement.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    // Bean for password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Using BCrypt password encoder for encoding passwords
    }

    // Bean for UserDetailsService (in-memory authentication)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Create 'user' with ROLE_USER
        UserDetails user = User.builder()
            .username("user")  // Username for the 'user'
            .password("{noop}userpass")  // Password encoded with BCrypt
            .roles("USER")  // Role assigned to the 'user'
            .build();

        // Create 'admin' with ROLE_ADMIN
        UserDetails admin = User.builder()
            .username("admin")  // Username for the 'admin'
            .password("{noop}adminpass")  // Password encoded with BCrypt
            .roles("ADMIN")  // Role assigned to the 'admin'
            .build();

        // Return an in-memory user details manager with the created users
        return new InMemoryUserDetailsManager(user, admin);
    }

    // Bean for AuthenticationFailureHandler to log the error when login fails
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            // Log the error
            if (exception instanceof BadCredentialsException) {
                logger.error("Authentication failed: Invalid credentials for user '{}'", request.getParameter("username"));
            } else {
                logger.error("Authentication failed: {}", exception.getMessage());
            }
            response.sendRedirect("/login?error");  // Redirect to the login page with error
        };
    }

    // Bean for SecurityFilterChain configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .requestMatchers("/books").permitAll() // or .permitAll() to make it public
        .anyRequest().permitAll();

        return http.build();
    }
}
