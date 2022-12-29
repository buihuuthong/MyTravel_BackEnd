package com.BuiHuuThong.MyTravel.authentication;

import com.google.firebase.auth.FirebaseAuthException;

import java.util.Map;

public interface FirebaseService {
    String generateToken(Map<String, Object> claims) throws FirebaseAuthException;
    Map<String, Object> extractAllClaims(String idToken) throws FirebaseAuthException;
    boolean isTokenExpired(String idToken) throws FirebaseAuthException;
    String extractUid(String idToken) throws FirebaseAuthException;
}