package com.demo.secure.apis.Secure.REST.APIs.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom implementation of AuthenticationEntryPoint for handling authentication errors.
 * This class sends an Unauthorized response for authentication-related errors.
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles authentication errors by sending an Unauthorized response.
     *
     * @param request       HTTP request
     * @param response      HTTP response
     * @param authException Authentication exception
     * @throws IOException if an I/O error occurs during the handling of the error
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
