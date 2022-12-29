package com.BuiHuuThong.MyTravel.authentication;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private final FirebaseService firebaseService;

    @Autowired
    public FirebaseAuthenticationFilter(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String idToken = request.getHeader("Authorization");
        if (idToken != null && idToken.startsWith("Bearer ")) {
            idToken = idToken.substring(7);
            try {
                Map<String, Object> claims = firebaseService.extractAllClaims(idToken);
                if (!firebaseService.isTokenExpired(idToken)) {
                    FirebaseAuthenticationToken authentication =
                            new FirebaseAuthenticationToken(claims.toString(), idToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (FirebaseAuthException e) {
                // Invalid token
            }
        }
        filterChain.doFilter(request, response);
    }
}