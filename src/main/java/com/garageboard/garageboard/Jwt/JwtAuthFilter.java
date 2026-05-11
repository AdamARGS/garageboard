package com.garageboard.garageboard.Jwt;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;

import com.garageboard.garageboard.User.User;
import com.garageboard.garageboard.User.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    JwtService jwt;
    UserRepository userRepository;

    public JwtAuthFilter(JwtService jwt, UserRepository userRepository) {
        this.jwt = jwt;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);

            if (jwt.validateToken(token)) {
                long id = jwt.extractUserId(token);
                Optional<User> user = userRepository.findById(id);

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                if (user.isPresent()) {
                    // TODO: add user roles
                    Authentication authentication = new UsernamePasswordAuthenticationToken(user.get(), null,
                            Collections.emptyList());
                    context.setAuthentication(authentication);
                }

                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }

}
