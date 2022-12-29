package com.BuiHuuThong.MyTravel.authentication;

import com.BuiHuuThong.MyTravel.dto.request.FirebaseAuthRequest;
import com.BuiHuuThong.MyTravel.repository.UserRepository;
import com.BuiHuuThong.MyTravel.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class FirebaseServiceImpl implements FirebaseService {
    private final FirebaseAuth firebaseAuth;
    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public FirebaseServiceImpl(FirebaseAuth firebaseAuth, UserRepository userRepository, UserService userService) {
        this.firebaseAuth = firebaseAuth;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public String generateToken(FirebaseAuthRequest authRequest) throws Exception {
        // Validate the request
        if (StringUtils.isEmpty(authRequest.getEmail()) || StringUtils.isEmpty(authRequest.getPassword())) {
            throw new IllegalArgumentException("Email and password are required");
        }
        // Check if the user exists in the database
        var user = userService.layUser(authRequest.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Check if the password is correct
        if(!passwordEncoder().matches(authRequest.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // Generate a Firebase token for the user
        String token = firebaseAuth.createCustomToken(user.getUid());
        return token;
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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