package com.webApp.controllers;

import com.webApp.exceptions.coupon.CouponUnauthorizedAccessException;
import com.webApp.exceptions.client.ClientUnauthorizedException;
import com.webApp.exceptions.client.ClientUnauthorizedIdException;
import com.webApp.models.Coupon;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;
import com.webApp.exceptions.client.TokenExpiredException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.ClientPermissionDeniedException;
import com.webApp.services.CompanyService;
import com.webApp.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompanyController {
    private CompanyService companyService;
    private TokenService tokenService;

    public CompanyController(CompanyService companyService, TokenService tokenService) {
        this.companyService = companyService;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/companies/{companyId}/coupons")
    public ResponseEntity<String> addCoupon(@PathVariable Long companyId, @RequestBody Coupon coupon,  @AuthenticationPrincipal Jwt principal){
        System.out.println("hello");
        try {
            accessControl(principal, companyId);
            isCompanyCoupon(companyId, coupon);
            long generatedId = companyService.addCoupon(companyId, coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(generatedId));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PutMapping("/api/companies/{companyId}/coupons/{couponId}")
    public ResponseEntity<String> updateCoupon(@PathVariable Long companyId, @PathVariable Long couponId,
                                               @RequestBody Coupon coupon,  @AuthenticationPrincipal Jwt principal){
        try {
            accessControl(principal, companyId);
            isCompanyCoupon(companyId, coupon);
            companyService.updateCoupon(companyId, coupon);
            return ResponseEntity.ok("Updated a coupon " + coupon);
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @DeleteMapping("/api/companies/{companyId}/coupons/{couponId}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long companyId, @PathVariable Long couponId,  @AuthenticationPrincipal Jwt principal){
        try {
            accessControl(principal, companyId);
//            isCompanyCoupon(companyId, coupon); inside service method
            companyService.deleteCoupon(companyId, couponId);
            return ResponseEntity.ok("Deleted a coupon " + couponId);
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/companies/{companyId}/coupons")
    public ResponseEntity<?> getCompanyCoupons(@PathVariable Long companyId,  @AuthenticationPrincipal Jwt principal){
        try {
            accessControl(principal, companyId);
            return ResponseEntity.ok(companyService.getAllCompanyCoupons(companyId));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/companies/{companyId}/coupons/{couponId}")
    public ResponseEntity<?> getCoupon(@PathVariable Long companyId, @PathVariable Long couponId,  @AuthenticationPrincipal Jwt principal){
        try {
            accessControl(principal, companyId);
            return ResponseEntity.ok(companyService.getOneCompanyCoupon(couponId));
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @GetMapping("/api/companies/{companyId}/info")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<?> getCompanyDetails(@PathVariable Long companyId,  @AuthenticationPrincipal Jwt principal){
        try {
            accessControl(principal, companyId);
            return ResponseEntity.ok(companyService.getCompanyDetails(companyId));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    private void companyAuthenticatedElseThrow(Jwt principal, Long companyId) throws ClientNotLoggedException, TokenExpiredException {
//        if (companyId == null)
//            throw new ClientNotLoggedException(ClientType.Company);
        if(tokenService.hasTokenExpired(companyId, ClientType.Company))
            throw new TokenExpiredException();
        tokenService.updateLastActivity(companyId, ClientType.Company);
    }
    private void companyAuthorizedElseThrow(Jwt principal, Long companyId) throws
            ClientUnauthorizedException, ClientUnauthorizedIdException {
        if (!principal.getClaims().get("scope").equals("ROLE_COMPANY"))
            throw new ClientUnauthorizedException(ClientType.Company);
        if (!(principal.getClaims().get("clientId").equals(companyId)))
            throw new ClientUnauthorizedIdException(ClientType.Company, companyId);
    }
    private void accessControl(Jwt principal, Long companyId) throws ClientNotLoggedException,TokenExpiredException,
            ClientUnauthorizedException, ClientUnauthorizedIdException {
        companyAuthenticatedElseThrow(principal, companyId);
        companyAuthorizedElseThrow(principal, companyId);
    }
    private void isCompanyCoupon(long companyId, Coupon coupon) throws CouponUnauthorizedAccessException {
        if(companyId != coupon.getCompany().getId())
            throw new CouponUnauthorizedAccessException();
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
