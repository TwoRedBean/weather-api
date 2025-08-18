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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${app.api-keys}")
    private String apiKeysString;
    @Value("${app.api-window-limit}")
    private int windowLimit;
    @Value("${app.api-window-hours}")
    private int windowHours;

    private Set<String> apiKeys;
    private final Map<String, ApiUsage> usageMap = new ConcurrentHashMap<>();

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

        if (!isValidApiKey(requestApiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain");
            response.getWriter().write("Unauthorized: Invalid or missing API key");
            return;
        }

        if (isRateLimitExceeded(requestApiKey)) {
            response.setStatus(429);
            response.setContentType("text/plain");
            response.getWriter().write("Rate limit exceeded: Max " + windowLimit + " requests per " + windowHours + " hour(s).");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isValidApiKey(String apiKey) {
        return apiKey != null && apiKeys.contains(apiKey.trim());
    }

    private boolean isRateLimitExceeded(String apiKey) {
        ApiUsage usage = usageMap.computeIfAbsent(apiKey, k -> new ApiUsage());

        // Change to concurrent hashmap for better performance
        synchronized (usage) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime resetTime = usage.startTime.plusHours(windowHours);

            if (now.isAfter(resetTime)) {
                usage.startTime = now;
                usage.count = 0;
            }

            if (usage.count >= windowLimit) {
                return true;
            }

            usage.count++;
            return false;
        }
    }

    private static class ApiUsage {
        private LocalDateTime startTime = LocalDateTime.now();
        private int count = 0;
    }
}
