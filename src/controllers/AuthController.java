package com.webApp.controllers;

import com.webApp.services.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = "*", maxAge = 3600)
@RestController
public class AuthController {
    private TokenService tokenService;
    private AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

//    @PostMapping("/token")
//    public ResponseEntity<?> token(@RequestBody LoginData loginData){
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginData.getEmail(), loginData.getPassword()
//        ));
//        return ResponseEntity.ok(tokenService.generateToken(authentication));
//    }
}
