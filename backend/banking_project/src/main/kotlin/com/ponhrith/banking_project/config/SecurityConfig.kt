package com.ponhrith.banking_project.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http
            .cors().and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/v1/profile","/api/v1/profile/*","/api/v1/auth/*").permitAll() // Permit access to this endpoint without authentication
            .anyRequest().authenticated().and()
            .formLogin().and()
            .httpBasic()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().applyPermitDefaultValues()
        configuration.allowedOrigins = listOf("http://127.0.0.1:5500")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}
