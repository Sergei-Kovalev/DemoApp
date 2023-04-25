package ru.ngs.summerjob.DemoApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> {
                    try {
                        auth
                                .requestMatchers(HttpMethod.GET,"/test").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET,"/getAllTasks").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET,"/getOverdueTasks").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET,"/tasks").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.GET,"/tasks/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/tasks/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST,"/tasks").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/tasks").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/themes/**").hasRole("ADMIN")
                                .anyRequest().authenticated().and().formLogin().permitAll().and().httpBasic();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
}
