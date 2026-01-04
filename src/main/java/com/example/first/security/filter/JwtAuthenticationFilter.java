package com.example.first.security.filter;

import com.example.first.security.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // Пропускаем публичные эндпоинты
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        // если токена нет, то юзер - анонимный, разрешаем публичные ендпоинты
        if (authHeader == null || authHeader.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        // проверка формата токена
        if (!authHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "invalid token format");
            return;
        }

        final String jwtToken = authHeader.substring(7);

        if (!jwtService.isValidToken(jwtToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid or expired");
            return;
        }

        final Long userId = jwtService.extractUserId(jwtToken);
        final String role = jwtService.extractRole(jwtToken);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userId, null, jwtService.getAuthorities(role));
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);


        filterChain.doFilter(request, response);
    }
}
