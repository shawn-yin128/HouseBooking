package com.project.hb.filter;

import com.project.hb.model.user.Authority;
import com.project.hb.repository.user.AuthorityRepository;
import com.project.hb.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class JwtFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private AuthorityRepository authorityRepository;
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HEADER);

        String jwt = null;
        if (authHeader != null && authHeader.startsWith(PREFIX)) {
            jwt = authHeader.substring(PREFIX.length());
        }

        if (jwt != null && jwtUtils.validateToken(jwt) && SecurityContextHolder.getContext().getAuthentication() == null) {
            String username = jwtUtils.extractUsername(jwt);
            Authority authority = authorityRepository.findById(username).orElse(null);
            if (authority != null) {
                List<GrantedAuthority> auths = Arrays.asList(new GrantedAuthority[]{new SimpleGrantedAuthority(authority.getAuthority())});
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, auths);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
