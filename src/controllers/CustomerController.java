package com.webApp.controllers;

import com.webApp.models.dto.LoginData;
import com.webApp.services.LoginManager;
import com.webApp.Token;
import com.webApp.TokenManager;
import com.webApp.exceptions.client.ClientNotAuthorizedException;
import com.webApp.models.Coupon;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;
import com.webApp.exceptions.client.TokenExpiredException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.ClientPermissionDeniedException;
import com.webApp.models.dto.LoginResponse;
import com.webApp.services.CustomerService;
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

@CrossOrigin(exposedHeaders = "*",maxAge = 3600)
@RestController
public class CustomerController extends ClientController{
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    TokenManager tokenManager;
    private CustomerService customerService;
    public CustomerController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager){
        super(companyRepository, couponRepository, customerRepository, loginManager);
    }

    @GetMapping("/customers/{customerId}")
    public ModelAndView showCustomer(@PathVariable Long customerId){
        if(customerService == null)
            return new ModelAndView("redirect:/login/customer");
        return new ModelAndView("forward:/html/customer.html");
    }

    @PostMapping("/api/login/customer")
    public ResponseEntity<?> Login(@RequestBody LoginData loginData){
        System.out.println("trying customer login");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(), loginData.getPassword()
            ));
            System.out.println("Email :" + loginData.getEmail());
            System.out.println("Password :" + loginData.getPassword());

            customerService = (CustomerService)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Customer);
            System.out.println("customer logged");
            return ResponseEntity.ok(new LoginResponse(
                    tokenService.generateToken(authentication, customerService.getCustomerID()),
                    customerService.getCustomerID())
            );
        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
//        try {
//            customerService = (CustomerService)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Customer);
//            return ResponseEntity.ok(tokenManager.createCustomerToken(customerService.getCustomerID()));
//        } catch (CustomException e){
//            return handleExceptions(e);
//        }
    }

//    @GetMapping("/api/customers/{customerId}/coupons")
////    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
//    public ResponseEntity<?> getAllCoupons(@PathVariable Long customerId, @RequestHeader("Authorization") String token){
//        try {
//            customerAuthenticatedElseThrow(principal);
//            return ResponseEntity.ok(customerService.getAllCoupons());
//        } catch (CustomException e){
//            return handleExceptions(e);
//        }
//    }

    @GetMapping("/api/customers/{customerId}/coupons/{id}")
//    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getCoupon(@PathVariable Long customerId, @PathVariable Long id, @AuthenticationPrincipal Jwt principal){
        System.out.println(principal.getClaims());
        try {
//            isCustomerLoggedElseThrow(customerId, new Token(token));
            customerAuthenticatedElseThrow(principal);

            return ResponseEntity.ok(customerService.getCoupon(id));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PostMapping("/api/customers/{customerId}/purchases")
//    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<String> purchaseCoupon(@PathVariable Long customerId, @RequestBody Coupon coupon, @AuthenticationPrincipal Jwt principal){
        try {
            customerAuthenticatedElseThrow(principal);
            customerService.purchaseCoupon(coupon.getId());
            return ResponseEntity.ok("Customer " + customerId + " purchased a coupon " + coupon.getId());
        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/api/customers/{customerId}/purchases")
//    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getCustomerPurchasedCoupons(@PathVariable Long customerId, @AuthenticationPrincipal Jwt principal){
        try {
//            isCustomerLoggedElseThrow(customerId, new Token(token));
            customerAuthenticatedElseThrow(principal);
            return ResponseEntity.ok(customerService.getCustomerPurchasedCoupons());
        } catch (CustomException e){
            return handleExceptions(e);
        } catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/api/customers/{customerId}/info")
//    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> getCustomerInfo(@PathVariable Long customerId, @AuthenticationPrincipal Jwt principal){
        try {
            customerAuthenticatedElseThrow(principal);
            return ResponseEntity.ok(customerService.getCustomerDetails());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    private void isCustomerLoggedElseThrow(Long requestCustomerId, Token token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, TokenExpiredException {
        if(this.customerService == null)
            throw new ClientNotLoggedException(ClientType.Customer);
        if(!tokenManager.isAuthenticated(requestCustomerId, ClientType.Customer, token))
            throw new ClientPermissionDeniedException(ClientType.Customer, requestCustomerId);
    }
    private void customerAuthenticatedElseThrow(Jwt principal) throws ClientNotLoggedException, ClientNotAuthorizedException {
        if (this.customerService == null)
            throw new ClientNotLoggedException(ClientType.Customer);
        if (!principal.getClaims().get("scope").equals("ROLE_CUSTOMER")) {
            throw new ClientNotAuthorizedException(ClientType.Customer);
        }
    }
    private ResponseEntity<String> handleExceptions(CustomException e){
        if (e.getClass().equals(ClientNotLoggedException.class) || e.getClass().equals(ClientNotAuthorizedException.class)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
