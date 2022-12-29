package com.BuiHuuThong.MyTravel.authentication;

import com.BuiHuuThong.MyTravel.dto.request.FirebaseAuthRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.Map;

public interface FirebaseService {

    String generateToken(FirebaseAuthRequest authRequest) throws Exception;
    Map<String, Object> extractAllClaims(String idToken) throws FirebaseAuthException;
    boolean isTokenExpired(String idToken) throws FirebaseAuthException;
    String extractUid(String idToken) throws FirebaseAuthException;
}