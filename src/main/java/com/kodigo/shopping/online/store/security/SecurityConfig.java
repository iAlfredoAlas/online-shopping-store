package com.kodigo.shopping.online.store.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Deshabilita la autenticación para todas las rutas
        http
                .authorizeRequests()
                .anyRequest().permitAll();

        // Otras configuraciones de seguridad si las tienes

        // Deshabilita la protección CSRF para simplificar
        http.csrf().disable();
    }
}
