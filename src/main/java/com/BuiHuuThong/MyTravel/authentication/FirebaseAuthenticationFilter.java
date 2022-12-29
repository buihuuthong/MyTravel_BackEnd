package com.BuiHuuThong.MyTravel.authentication;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FirebaseAuthenticationFilter extends OncePerRequestFilter {

    private final FirebaseService firebaseService;

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