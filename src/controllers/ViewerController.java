package com.webApp.controllers;

import com.webApp.services.ViewerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(exposedHeaders = "*",maxAge = 3600)
@RestController
public class ViewerController{
    private ViewerService viewerService;
    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @GetMapping("/home")
    public ModelAndView showHome(){
        return new ModelAndView("forward:/html/home.html");
    }

    @GetMapping("/signup")
    public ModelAndView showSignup(){
        return new ModelAndView("forward:/html/customer-create.html");
    }

    @GetMapping({"/login", "/login/admin", "/login/customer", "/login/company"})
    public ModelAndView showLogin(){
        return new ModelAndView("forward:/html/login.html");
    }

    @PostMapping("/home")
    public ResponseEntity<String> test(@AuthenticationPrincipal Jwt principal) {
        System.out.println(principal.getClaims());
        return ResponseEntity.ok(principal.toString());
    }
    @GetMapping("/api/public/coupons")
    public ResponseEntity<?> getAllCoupons(){
        return ResponseEntity.ok(viewerService.getAllCoupons());
    }
//    @GetMapping("/api/public/coupons/{couponID}")
//    public ResponseEntity<?> getAllCoupons(@PathVariable long couponID){
//        return ResponseEntity.ok(viewerService.getCoupon(couponID));
//    }
}
