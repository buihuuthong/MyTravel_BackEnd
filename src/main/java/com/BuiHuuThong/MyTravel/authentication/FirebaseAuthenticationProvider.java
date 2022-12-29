package com.BuiHuuThong.MyTravel.authentication;

import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class FirebaseAuthenticationProvider implements AuthenticationProvider {

    private final FirebaseService firebaseService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String idToken = (String) authentication.getCredentials();
        try {
            if (firebaseService.isTokenExpired(idToken)) {
                throw new BadCredentialsException("Firebase token expired");
            }
            String uid = firebaseService.extractUid(idToken);
            Map<String, Object> claims = firebaseService.extractAllClaims(idToken);
            return new FirebaseAuthenticationToken(uid, idToken, claims);
        } catch (FirebaseAuthException e) {
            throw new BadCredentialsException("Invalid Firebase token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FirebaseAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
