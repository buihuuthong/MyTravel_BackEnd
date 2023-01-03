package com.BuiHuuThong.MyTravel.controller;

import com.BuiHuuThong.MyTravel.authentication.FirebaseService;
import com.BuiHuuThong.MyTravel.dto.request.FirebaseAuthRequest;
import com.BuiHuuThong.MyTravel.dto.response.FirebaseAuthResponse;
import com.BuiHuuThong.MyTravel.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "FirebaseAuthController", description = "Các API test")
public class FirebaseAuthController {
    private final FirebaseService firebaseService;

    private final JdbcTemplate jdbcTemplate;

    @PostMapping("/generate-token")
    public ResponseEntity<FirebaseAuthResponse> generateToken(@RequestBody FirebaseAuthRequest authRequest) throws Exception {
        // Validate the request and generate a Firebase token
        String token = firebaseService.generateToken(authRequest);

        // Return the token in the response
        FirebaseAuthResponse authResponse = new FirebaseAuthResponse();
        authResponse.setToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "FirebaseToken")
    @Operation(summary = "Lấy danh sách user")
    public List<User> getUsers() {
        String sql = "SELECT * FROM TaiKhoan";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }
}
