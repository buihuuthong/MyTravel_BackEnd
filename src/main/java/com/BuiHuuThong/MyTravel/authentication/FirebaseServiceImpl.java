package com.BuiHuuThong.MyTravel.authentication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private final FirebaseAuth firebaseAuth;

    @Autowired
    public FirebaseServiceImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public String generateToken(Map<String, Object> claims) throws FirebaseAuthException {
        return firebaseAuth.createCustomToken(claims.toString());
    }

    @Override
    public Map<String, Object> extractAllClaims(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
        return decodedToken.getClaims();
    }

    @Override
    public boolean isTokenExpired(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
//        Date expirationDate = decodedToken.getExpirationTime();
//        return expirationDate.before(new Date());
        return true;
    }
    @Override
    public String extractUid(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
        return decodedToken.getUid();
    }

}