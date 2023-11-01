package com.Binusa.BawasluServer.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtRequestFilter extends UsernamePasswordAuthenticationFilter {

    private final UserDetailsService userDetailsService;

    @Value("${myapp.secretKey}")
    private String secretKey;

    private final long validityInMilliseconds;

    public JwtRequestFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.validityInMilliseconds = 864000000; // Isi nilai validityInMilliseconds sesuai kebutuhan
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = extractTokenFromRequest((HttpServletRequest) request);

        if (token != null && validateToken(token)) {
            String username = getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (userDetails != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    private String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    private boolean validateToken(String token) {
        Date expirationDate = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        return expirationDate.after(new Date());
    }
}
