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
import org.springframework.web.servlet.DispatcherServlet

@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    private lateinit var customUserDetailsService: AuthService

    @Autowired
    private lateinit var jwtRequestFilter: JwtRequestFilter

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        super.configure(auth)
        auth.userDetailsService(customUserDetailsService)
    }

    @Throws(Exception::class)
    override fun configure(security: HttpSecurity) {
        security.csrf().disable()
            .authorizeRequests()
            .antMatchers("/authenticate", "/login", "/signup").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        // Register JwtRequestFilter before UsernamePasswordAuthenticationFilter
        security.addFilterBefore(jwtRequestFilter, Filter::class.java)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    @Throws(Exception::class)
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    @Bean
    fun jwtRequestFilter(): JwtRequestFilter {
        return JwtRequestFilter()
    }

}
