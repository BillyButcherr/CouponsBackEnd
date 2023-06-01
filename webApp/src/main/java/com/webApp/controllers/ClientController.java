package com.webApp.controllers;

import com.webApp.models.dto.LoginData;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.client.ClientIdDoesNotExistException;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.http.ResponseEntity;

public abstract class ClientController {
    protected CompanyRepository companyRepository;
    protected CouponRepository couponRepository;
    protected CustomerRepository customerRepository;

    public ClientController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
    }
    public abstract ResponseEntity<?> Login(LoginData loginData) throws ClientDoesNotExistException, ClientIdDoesNotExistException;
}
