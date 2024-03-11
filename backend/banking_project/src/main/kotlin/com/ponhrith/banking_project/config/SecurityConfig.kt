package com.ponhrith.banking_project.config

import com.ponhrith.banking_project.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import javax.servlet.Filter
import jakarta.servlet.FilterRegistration
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.DispatcherServlet

@Configuration
@EnableWebSecurity
class SecurityConfig(private val customUserDetailsService: AuthService) : WebSecurityConfigurerAdapter() {

    @Bean
    fun jwtRequestFilter(): JwtRequestFilter {
        return JwtRequestFilter(customUserDetailsService)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(customUserDetailsService)
    }

    override fun configure(security: HttpSecurity) {
        security.csrf().disable()
            .authorizeRequests()
            .antMatchers("/authenticate", "/login", "/signup", "/api/v1/profile**").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Retrieve the JwtRequestFilter bean from the Spring context
        val filter = jwtRequestFilter()

        // Register JwtRequestFilter before UsernamePasswordAuthenticationFilter
        security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}