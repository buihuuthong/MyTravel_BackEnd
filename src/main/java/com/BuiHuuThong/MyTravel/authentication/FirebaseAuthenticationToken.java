package com.BuiHuuThong.MyTravel.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Map;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

    private final String uid;
    private final String idToken;
    private final Map<String, Object> claims;

    public FirebaseAuthenticationToken(String uid, String idToken) {
        super(null);
        this.uid = uid;
        this.idToken = idToken;
        this.claims = null;
        setAuthenticated(true);
    }

    public FirebaseAuthenticationToken(String uid, String idToken, Map<String, Object> claims) {
        super(null);
        this.uid = uid;
        this.idToken = idToken;
        this.claims = claims;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return idToken;
    }

    @Override
    public Object getPrincipal() {
        return uid;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }
}
