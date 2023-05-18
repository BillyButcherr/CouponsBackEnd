package com.webApp.controllers;

import com.webApp.exceptions.client.*;
import com.webApp.models.Coupon;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;
import com.webApp.services.CustomerService;
import com.webApp.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController{
    private CustomerService customerService;
    private TokenService tokenService;

    public CustomerController(CustomerService customerService, TokenService tokenService) {
        this.customerService = customerService;
        this.tokenService = tokenService;
    }

    @GetMapping("/api/customers/{customerId}/)")
    public ResponseEntity<?> getAllCoupons(@AuthenticationPrincipal Jwt principal, @PathVariable Long customerId){
        try {
            accessControl(principal, customerId);
            return ResponseEntity.ok(customerService.getAllCoupons());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }

    @GetMapping("/api/customers/{customerId}/coupons/{id}")
    public ResponseEntity<?> getCoupon(@AuthenticationPrincipal Jwt principal, @PathVariable Long customerId, @PathVariable Long id){
        System.out.println(principal.getClaims());
        try {
            accessControl(principal, customerId);
            return ResponseEntity.ok(customerService.getCoupon(id));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PostMapping("/api/customers/{customerId}/purchases")
    public ResponseEntity<String> purchaseCoupon(@AuthenticationPrincipal Jwt principal, @PathVariable Long customerId, @RequestBody Coupon coupon){
        try {
            accessControl(principal, customerId);
            customerService.purchaseCoupon(customerId, coupon.getId());
            return ResponseEntity.ok("Customer " + customerId + " purchased a coupon " + coupon.getId());
        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/api/customers/{customerId}/purchases")
    public ResponseEntity<?> getCustomerPurchasedCoupons(@AuthenticationPrincipal Jwt principal, @PathVariable Long customerId){
        try {
            accessControl(principal, customerId);
            return ResponseEntity.ok(customerService.getCustomerPurchasedCoupons(customerId));
        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/api/customers/{customerId}/info")
//    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getCustomerInfo(@AuthenticationPrincipal Jwt principal, @PathVariable Long customerId){
        try {
            accessControl(principal, customerId);
            return ResponseEntity.ok(customerService.getCustomerDetails(customerId));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    private void customerAuthenticatedElseThrow(Jwt principal, Long customerId) throws ClientNotLoggedException,
            TokenExpiredException {
        if(tokenService.hasTokenExpired(customerId, ClientType.Customer))
            throw new TokenExpiredException();
        tokenService.updateLastActivity(customerId, ClientType.Customer);
    }
    private void customerAuthorizedElseThrow(Jwt principal, Long customerId) throws
            ClientUnauthorizedException, ClientUnauthorizedIdException {
        if (!principal.getClaims().get("scope").equals("ROLE_CUSTOMER"))
            throw new ClientUnauthorizedException(ClientType.Customer);
        if (!(principal.getClaims().get("clientId").equals(customerId)))
            throw new ClientUnauthorizedIdException(ClientType.Customer, customerId);
    }
    private void accessControl(Jwt principal, Long customerId) throws ClientNotLoggedException,TokenExpiredException,
            ClientUnauthorizedException, ClientUnauthorizedIdException {
        customerAuthenticatedElseThrow(principal, customerId);
        customerAuthorizedElseThrow(principal, customerId);
    }
    private ResponseEntity<String> handleExceptions(CustomException e){
        if (e.getClass().equals(ClientNotLoggedException.class) || e.getClass().equals(ClientUnauthorizedException.class)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
