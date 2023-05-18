package com.webApp.controllers;

import com.webApp.services.ViewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ViewerController{
    private ViewerService viewerService;
    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @GetMapping("/api/public/coupons")
    public ResponseEntity<?> getAllCoupons(){
        return ResponseEntity.ok(viewerService.getAllCoupons());
    }
}
