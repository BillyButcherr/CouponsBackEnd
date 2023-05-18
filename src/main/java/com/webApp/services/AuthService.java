package com.webApp.services;

import com.webApp.Token;
import com.webApp.controllers.AdminController;
import com.webApp.controllers.CompanyController;
import com.webApp.controllers.CustomerController;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.models.dto.LoginData;
import com.webApp.models.enums.ClientType;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final ConfigurableApplicationContext context;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    public AuthService(ConfigurableApplicationContext context, AdminService adminService, CompanyService companyService, CustomerService customerService, TokenService tokenService, AuthenticationManager authenticationManager, CompanyRepository companyRepository, CustomerRepository customerRepository) {
        this.context = context;
        this.adminService = adminService;
        this.companyService = companyService;
        this.customerService = customerService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
    }

    public Token authenticateAdmin(LoginData loginData) throws AuthenticationException {
        System.out.println("trying admin login");
        Token token = authenticateElseThrow(loginData, 0, ClientType.Administrator);
        System.out.println("admin logged");
        return token;
    }
    public Token authenticateCompany(LoginData loginData) throws AuthenticationException, CompanyDoesNotExistException {
        System.out.println("trying company login");
        System.out.println("Email :" + loginData.getEmail());
        System.out.println("Password :" + loginData.getPassword());
        long companyId = companyRepository.findByEmail(loginData.getEmail())
                .orElseThrow(CompanyDoesNotExistException::new).getId();
        Token token = authenticateElseThrow(loginData, companyId, ClientType.Company);
        System.out.println("company logged");
        return token;
    }
    public Token authenticateCustomer(LoginData loginData) throws AuthenticationException, CustomerDoesNotExistException {
        System.out.println("trying customer login");
        System.out.println("Email :" + loginData.getEmail());
        System.out.println("Password :" + loginData.getPassword());
        long customerId = customerRepository.findByEmail(loginData.getEmail())
                .orElseThrow(CustomerDoesNotExistException::new).getId();
        Token token = authenticateElseThrow(loginData, customerId, ClientType.Customer);
        System.out.println("customer logged");
        return token;
    }
    private Token authenticateElseThrow(LoginData loginData, long clientId, ClientType clientType) throws AuthenticationException{
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginData.getEmail(), loginData.getPassword()
        ));
        return tokenService.generateToken(authentication, clientId, clientType);
    }
}
