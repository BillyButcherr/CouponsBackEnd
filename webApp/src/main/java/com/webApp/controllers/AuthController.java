package com.webApp.controllers;

import com.webApp.Token;
import com.webApp.exceptions.CustomException;
import com.webApp.exceptions.client.*;
import com.webApp.models.dto.LoginData;
import com.webApp.models.dto.LoginResponse;
import com.webApp.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController{
    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/auth/admin")
    public ResponseEntity<?> adminLogin(@RequestBody LoginData loginData){
    try {
        Token token = authService.authenticateAdmin(loginData);
        return ResponseEntity.ok(new LoginResponse(
                token.getData(),
                0)
        );
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }
    @PostMapping("/api/auth/company")
    public ResponseEntity<?> companyLogin(@RequestBody LoginData loginData){
        try {
            Token token = authService.authenticateCompany(loginData);

            return ResponseEntity.ok(new LoginResponse(
                    token.getData(),
                    token.getClientId())
            );

        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @PostMapping("/api/auth/customer")
    public ResponseEntity<?> customerLogin(@RequestBody LoginData loginData) {
        try {
            Token token = authService.authenticateCustomer(loginData);
            return ResponseEntity.ok(new LoginResponse(
                    token.getData(),
                    token.getClientId())
            );
        } catch (CustomException e) {
            return handleExceptions(e);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
    private ResponseEntity<String> handleExceptions(CustomException e){
        if (e.getClass().equals(ClientNotLoggedException.class)
                || e.getClass().equals(ClientPermissionDeniedException.class)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
