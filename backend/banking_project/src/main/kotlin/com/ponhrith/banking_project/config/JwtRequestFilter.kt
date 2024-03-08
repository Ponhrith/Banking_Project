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
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.logging.Filter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse


@Component
class JwtRequestFilter : GenericFilterBean(), Filter {
    @Autowired
    private lateinit var jwtUtil: JwtUtil

    @Autowired
    private lateinit var userDetailsService: AuthService

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val authorizationHeader = httpRequest.getHeader("Authorization")
        val jwtToken: String?
        val username: String?
        val bearerToken = httpRequest.getHeader("Authorization")
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            jwtToken = bearerToken.substring(7, bearerToken.length)
            username = jwtUtil.extractUsername(jwtToken)
            if (SecurityContextHolder.getContext().authentication == null) {
                val userDetails = userDetailsService.loadUserByUsername(username)
                if (jwtUtil.validateToken(jwtToken, userDetails!!)) {
                    val usernamePasswordAuthenticationToken =
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(httpRequest)
                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                }
            }
        }
        chain.doFilter(request, response)
    }
}