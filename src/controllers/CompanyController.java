package com.webApp.controllers;

import com.webApp.models.dto.LoginData;
import com.webApp.services.LoginManager;
import com.webApp.Token;
import com.webApp.TokenManager;
import com.webApp.exceptions.client.ClientNotAuthorizedException;
import com.webApp.exceptions.client.ClientUnauthorizedIdException;
import com.webApp.models.Coupon;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;
import com.webApp.exceptions.client.TokenExpiredException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.ClientPermissionDeniedException;
import com.webApp.exceptions.coupon.CouponDoesNotExistException;
import com.webApp.models.dto.LoginResponse;
import com.webApp.services.CompanyService;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;
import com.webApp.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.Objects;

@CrossOrigin(exposedHeaders = "*", maxAge = 3600)
@RestController
public class CompanyController extends ClientController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;
    private CompanyService companyService;
    public CompanyController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager) {
        super(companyRepository, couponRepository, customerRepository, loginManager);
    }
//    @GetMapping("/login/company")
//    public ModelAndView showLogin(){
//        return new ModelAndView("forward:/html/login.html");
//    }

    @GetMapping("/companies/{companyId}")
    public ModelAndView showCompany(@PathVariable Long companyId){
        if(companyService == null)
            return new ModelAndView("redirect:/login/company");
        return new ModelAndView("forward:/html/company.html");
    }
    @PostMapping("/api/login/company")
    public ResponseEntity<?> Login(@RequestBody LoginData loginData){
        System.out.println("trying company login");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(), loginData.getPassword()
            ));
            System.out.println("Email :" + loginData.getEmail());
            System.out.println("Password :" + loginData.getPassword());

            companyService = (CompanyService)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Company);
            System.out.println("company logged");
            return ResponseEntity.ok(new LoginResponse(
                    tokenService.generateToken(authentication, companyService.getCompanyID()),
                    companyService.getCompanyID())
            );

//            return ResponseEntity.ok(tokenService.generateToken(authentication, companyService.getCompanyID()));

//            return ResponseEntity.ok(tokenManager.createCompanyToken(companyService.getCompanyID()));
        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @PostMapping("/api/companies/{companyId}/coupons")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<String> addCoupon(@PathVariable Long companyId, @RequestBody Coupon coupon,  @AuthenticationPrincipal Jwt principal){
        try {
            companyAuthenticatedElseThrow(principal, companyId);
            companyService.addCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body("Added new coupon " + coupon);
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PutMapping("/api/companies/{companyId}/coupons/{couponId}")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<String> updateCoupon(@PathVariable Long companyId, @PathVariable Long couponId, @RequestBody Coupon coupon,  @AuthenticationPrincipal Jwt principal){
        try {
            companyAuthenticatedElseThrow(principal, companyId);
            companyService.updateCoupon(coupon);
            return ResponseEntity.ok("Updated a coupon " + coupon);
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @DeleteMapping("/api/companies/{companyId}/coupons/{couponId}")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long companyId, @PathVariable Long couponId,  @AuthenticationPrincipal Jwt principal){
        try {
            companyAuthenticatedElseThrow(principal, companyId);
            companyService.deleteCoupon(couponId);
            return ResponseEntity.ok("Deleted a coupon " + couponId);
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/companies/{companyId}/coupons")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<?> getCompanyCoupons(@PathVariable Long companyId,  @AuthenticationPrincipal Jwt principal){
        try {
            companyAuthenticatedElseThrow(principal, companyId);
           return ResponseEntity.ok(companyService.getAllCompanyCoupons());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/companies/{companyId}/coupons/{couponId}")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<?> getCoupon(@PathVariable Long companyId, @PathVariable Long couponId,  @AuthenticationPrincipal Jwt principal){
        try {
            companyAuthenticatedElseThrow(principal, companyId);
            return ResponseEntity.ok(companyService.getAllCompanyCoupons().stream()
                    .filter(coupon -> coupon.getId() == couponId)
                    .findFirst()
                    .orElseThrow(CouponDoesNotExistException::new));
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @GetMapping("/api/companies/{companyId}/info")
//    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    public ResponseEntity<?> getCompanyDetails(@PathVariable Long companyId,  @AuthenticationPrincipal Jwt principal){
        try {
            companyAuthenticatedElseThrow(principal, companyId);
            return ResponseEntity.ok(companyService.getCompanyDetails());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    private void isCompanyLogged(Long requestCompanyId, Token token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, TokenExpiredException {
        if(this.companyService == null)
            throw new ClientNotLoggedException(ClientType.Company);
        if(!tokenManager.isAuthenticated(requestCompanyId, ClientType.Company, token))
            throw new ClientPermissionDeniedException(ClientType.Company, requestCompanyId);
    }
    private void companyAuthenticatedElseThrow(Jwt principal, Long companyId) throws ClientNotLoggedException,
            ClientNotAuthorizedException, TokenExpiredException, ClientUnauthorizedIdException {
        if (this.companyService == null)
            throw new ClientNotLoggedException(ClientType.Company);
        if(Instant.now().isAfter(Objects.requireNonNull(principal.getExpiresAt())))
            throw new TokenExpiredException();
        if (!principal.getClaims().get("scope").equals("ROLE_COMPANY"))
            throw new ClientNotAuthorizedException(ClientType.Company);
        if (!(principal.getClaims().get("clientId").equals(companyId)))
            throw new ClientUnauthorizedIdException(ClientType.Company, companyId);
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
