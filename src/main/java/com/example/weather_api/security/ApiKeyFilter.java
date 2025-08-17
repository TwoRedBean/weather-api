package com.example.weather_api.security;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-keys}")
    private String apiKeysString;

    private Set<String> apiKeys;

    @PostConstruct
    public void init() {
        apiKeys = new HashSet<>();
        if (apiKeysString != null && !apiKeysString.isBlank()) {
            apiKeys.addAll(Arrays.asList(apiKeysString.split(",")));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {

        String requestApiKey = request.getHeader("X-API-Key");

        if (requestApiKey == null || !apiKeys.contains(requestApiKey.trim())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().write("Unauthorized: Invalid or missing API key");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
