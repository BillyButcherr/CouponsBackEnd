package com.webApp.controllers;

import com.webApp.models.dto.LoginData;
import com.webApp.services.LoginManager;
import com.webApp.TokenManager;
import com.webApp.models.Company;
import com.webApp.models.Customer;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;
import com.webApp.exceptions.client.ClientNotAuthorizedException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.models.dto.LoginResponse;
import com.webApp.services.AdminService;
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

@CrossOrigin(exposedHeaders = "*", maxAge = 3600)
@RestController
public class AdminController extends ClientController{
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenManager tokenManager;
    private AdminService adminService;
    public AdminController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager){
        super(companyRepository, couponRepository, customerRepository, loginManager);
    }

//    @GetMapping("/login/admin")
//    public ModelAndView showLogin(){
//        return new ModelAndView("forward:/html/login.html");
//    }
    @GetMapping("/admin/customer-update")
    public ModelAndView showCustomerUpdate(){
        return new ModelAndView("forward:/html/customer-update.html");
    }
    @GetMapping("/admin")
    public ModelAndView showAdmin(){
        try{
            isAdminLoggedElseThrow();
            return new ModelAndView("forward:/html/admin.html");
        } catch (ClientNotLoggedException e){
            return new ModelAndView("redirect:/login/admin");
        }
    }

    @PostMapping("/api/login/admin")
    public ResponseEntity<?> Login(@RequestBody LoginData loginData){
        System.out.println("trying admin login");
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginData.getEmail(), loginData.getPassword()
            ));
            this.adminService = (AdminService)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Administrator);
            System.out.println("admin logged");
            return ResponseEntity.ok(new LoginResponse(
                    tokenService.generateToken(authentication, 0),
                    0)
            );
            //            return ResponseEntity.ok(tokenService.generateToken(authentication, 0));

//            return ResponseEntity.ok(tokenManager.createAdminToken());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @CrossOrigin(exposedHeaders = "*", maxAge = 3600)
    @PostMapping("api/admin/companies")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addCompany(@AuthenticationPrincipal Jwt principal, @RequestBody Company company) {
        System.out.println(principal.getClaims());
        try {
            AdminAuthenticatedElseThrow(principal);
            adminService.addCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED).body("Added new company " + company);
        } catch (CustomException e) {
            return handleExceptions(e);
        }
    }

    @PutMapping("/api/admin/companies")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateCompany(@RequestBody Company company){
        try {
            isAdminLoggedElseThrow();

            adminService.updateCompany(company);
            return ResponseEntity.ok("Updated a customer " + company.getId());
        }catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @DeleteMapping("/api/admin/companies/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCompany(@PathVariable Long id){
        try {
            isAdminLoggedElseThrow();
            adminService.deleteCompany(id);
            return ResponseEntity.ok("Deleted a company " + id);
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/admin/companies")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCompanies(){
        try {
            isAdminLoggedElseThrow();
            return ResponseEntity.ok(adminService.getAllCompaniesLazy());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/admin/companies/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getCompany(@PathVariable Integer id){
        try {
            isAdminLoggedElseThrow();
            return ResponseEntity.ok(adminService.getOneCompany(id));
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }

    @PostMapping("/api/admin/customers")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer){
        try {
            isAdminLoggedElseThrow();
            long customer1ID = adminService.addCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body("Added new customer " + customer);
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @PutMapping("/api/admin/customers")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer){
        try {
            isAdminLoggedElseThrow();
            adminService.updateCustomer(customer);
            return ResponseEntity.ok("Updated a customer " + customer.getId());
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @DeleteMapping("/api/admin/customers/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        try {
            isAdminLoggedElseThrow();
            adminService.deleteCustomer(id);
            return ResponseEntity.ok("Deleted a customer " + id);
        } catch (CustomException e){
            return handleExceptions(e);
        }

    }
    @GetMapping("/api/admin/customers")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllCustomers(){
        try {
            isAdminLoggedElseThrow();
            return ResponseEntity.ok(adminService.getAllCustomersLazy());
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    @GetMapping("/api/admin/customers/{id}")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getCustomer(@PathVariable Long id){
        try {
            isAdminLoggedElseThrow();
            return ResponseEntity.ok(adminService.getOneCustomer(id));
        } catch (CustomException e){
            return handleExceptions(e);
        }
    }
    private void isAdminLoggedElseThrow() throws ClientNotLoggedException {
        if(adminService == null)
            throw new ClientNotLoggedException(ClientType.Administrator);
    }
    private void AdminAuthenticatedElseThrow(Jwt principal) throws ClientNotLoggedException, ClientNotAuthorizedException {
        if(adminService == null)
            throw new ClientNotLoggedException(ClientType.Administrator);
        if(!principal.getClaims().get("scope").equals("ROLE_ADMIN")){
            throw new ClientNotAuthorizedException(ClientType.Administrator);
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
