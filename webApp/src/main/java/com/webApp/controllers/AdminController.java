package com.webApp.controllers;

import com.webApp.exceptions.client.TokenExpiredException;
import com.webApp.models.Company;
import com.webApp.models.Customer;
import com.webApp.models.dto.PasswordChangeData;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;
import com.webApp.exceptions.client.ClientUnauthorizedException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.services.AdminService;
import com.webApp.services.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(exposedHeaders = "*", maxAge = 3600)
@RestController
public class AdminController{
    private final AdminService adminService;
    private final TokenService tokenService;

    public AdminController(AdminService adminService, TokenService tokenService) {
        this.adminService = adminService;
        this.tokenService = tokenService;
    }

    @PostMapping("api/admin/companies")
    public ResponseEntity<String> addCompany(@AuthenticationPrincipal Jwt principal, @RequestBody Company company) {
        System.out.println(principal.getClaims());
        try {
            adminAuthenticatedElseThrow(principal);
            long generateId = adminService.addCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(generateId));
        } catch (CustomException e) {
            return handleExceptions(e);
        }
    }

    @PutMapping("/api/admin/companies")
    public ResponseEntity<String> updateCompany(@AuthenticationPrincipal Jwt principal, @RequestBody Company company){
        try {
            adminAuthenticatedElseThrow(principal);
            adminService.updateCompany(company);
            return ResponseEntity.ok("Updated a company " + company.getId());
        }catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PutMapping("/api/admin/companies/password")
    public ResponseEntity<String> updateCompanyPassword(@AuthenticationPrincipal Jwt principal, @RequestBody Company company){
        try {
            adminAuthenticatedElseThrow(principal);
            adminService.updateCompanyPassword(company);
            return ResponseEntity.ok("Updated a company " + company.getId());
        }catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @DeleteMapping("/api/admin/companies/{id}")
    public ResponseEntity<String> deleteCompany(@AuthenticationPrincipal Jwt principal, @PathVariable Long id){
        try {
            adminAuthenticatedElseThrow(principal);
            adminService.deleteCompany(id);
            return ResponseEntity.ok("Deleted a company " + id);
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/admin/companies")
    public ResponseEntity<?> getAllCompanies(@AuthenticationPrincipal Jwt principal){
        try {
            adminAuthenticatedElseThrow(principal);
            return ResponseEntity.ok(adminService.getAllCompaniesLazy());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/admin/companies/{id}")
    public ResponseEntity<?> getCompany(@AuthenticationPrincipal Jwt principal, @PathVariable Integer id){
        try {
            adminAuthenticatedElseThrow(principal);
            return ResponseEntity.ok(adminService.getOneCompany(id));
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }

    @PostMapping("/api/admin/customers")
    public ResponseEntity<String> addCustomer(@AuthenticationPrincipal Jwt principal, @RequestBody Customer customer){
        try {
            adminAuthenticatedElseThrow(principal);
            long generatedId = adminService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf(generatedId));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PutMapping("/api/admin/customers")
    public ResponseEntity<String> updateCustomer(@AuthenticationPrincipal Jwt principal, @RequestBody Customer customer){
        System.out.println("try update customer");
        try {
            adminAuthenticatedElseThrow(principal);
            adminService.updateCustomer(customer);
            return ResponseEntity.ok("Updated a customer " + customer.getId());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PutMapping("/api/admin/customers/password")
    public ResponseEntity<String> updateCustomerPassword(@AuthenticationPrincipal Jwt principal,
                                                         @RequestBody Customer customer){
        try {
            adminAuthenticatedElseThrow(principal);
            adminService.updateCustomerPassword(customer);
            return ResponseEntity.ok("Updated a customer password" + customer.getId());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @DeleteMapping("/api/admin/customers/{id}")
    public ResponseEntity<String> deleteCustomer(@AuthenticationPrincipal Jwt principal, @PathVariable Long id){
        System.out.println("hey");
        try {
            adminAuthenticatedElseThrow(principal);
            adminService.deleteCustomer(id);
            return ResponseEntity.ok("Deleted a customer " + id);
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @GetMapping("/api/admin/customers")
    public ResponseEntity<?> getAllCustomers(@AuthenticationPrincipal Jwt principal){
        try {
            adminAuthenticatedElseThrow(principal);
            return ResponseEntity.ok(adminService.getAllCustomersLazy());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/admin/customers/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getCustomer(@AuthenticationPrincipal Jwt principal, @PathVariable Long id){
        try {
            adminAuthenticatedElseThrow(principal);
            return ResponseEntity.ok(adminService.getOneCustomer(id));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    private void adminAuthenticatedElseThrow(Jwt principal) throws ClientNotLoggedException, ClientUnauthorizedException, TokenExpiredException {
        if(tokenService.hasTokenExpired(0, ClientType.Administrator))
            throw new TokenExpiredException();
        tokenService.updateLastActivity(0, ClientType.Administrator);
        if(!principal.getClaims().get("scope").equals("ROLE_ADMIN")){
            throw new ClientUnauthorizedException(ClientType.Administrator);
        }
    }
    private ResponseEntity<String> handleExceptions(CustomException e){
        if (e.getClass().equals(ClientNotLoggedException.class) || e.getClass().equals(ClientUnauthorizedException.class)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
