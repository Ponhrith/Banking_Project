package com.ponhrith.banking_project.config


import com.ponhrith.banking_project.service.AuthService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException


@Component
class JwtRequestFilter : OncePerRequestFilter() {
    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var userDetailsService: AuthService

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        val jwtToken: String?
        val username: String?
        val bearerToken = request.getHeader("Authorization")
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            jwtToken = bearerToken.substring(7, bearerToken.length)
            username = jwtUtil.extractUsername(jwtToken)
            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
                if (jwtUtil.validateToken(jwtToken, userDetails!!)) {
                    val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        }
        filterChain.doFilter(request, response)
    }
}