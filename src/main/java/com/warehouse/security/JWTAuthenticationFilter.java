package com.warehouse.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
* The `JWTAuthenticationFilter` class extends `OncePerRequestFilter`, a Spring Security filter class that guarantees only one execution per request.
* This class ensures that JWT-based authentication is applied to each incoming HTTP request exactly once.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private CustomUserDetailsService customUserDetailsService;

    public JWTAuthenticationFilter(JWTService jwtService, CustomUserDetailsService customUserDetailsService) {
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /*
     * The `doFilterInternal` Method is the core method that:
     *  1. Extracts the JWT from the `Authorization` header.
     *  2. Validates the JWT.
     *  3. Sets up the authenticated user's details into the Spring Security context if the JWT is valid.
     *  4. Passes the request and response to the next filter in the chain.
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJWTFromRequest(request); // Get Bearer token from Authorization Header of the request

        if(StringUtils.hasText(token) && jwtService.validateToken(token)) {

            String username = jwtService.getUsernameFromJWT(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            /*
            * An `UsernamePasswordAuthenticationToken` object is created to represent the authenticated user, with:
            * - The user's details (`userDetails`) from the database.
            * - `null` for the credentials because the user has already been authenticated (via the token).
            * - The user's roles/authorities (`userDetails.getAuthorities()`).
            */
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            // authenticationToken.setDetails() extra details about the request, such as remote IP and session ID
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // - sets the authentication object in the current security context. This step tells Spring Security that the user is authenticated.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // After setting up the security context, this passes the request and response to the next filters in the processing chain to handle the request
        filterChain.doFilter(request, response);

    }

    private String getJWTFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
