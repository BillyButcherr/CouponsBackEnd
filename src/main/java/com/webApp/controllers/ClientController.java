package com.webApp.controllers;

import com.webApp.LoginData;
import com.webApp.LoginManager;
import com.webApp.Token;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.client.ClientIdDoesNotExistException;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;

public abstract class ClientController {
    protected LoginManager loginManager;
    protected CompanyRepository companyRepository;
    protected CouponRepository couponRepository;
    protected CustomerRepository customerRepository;

    public ClientController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager) {
        this.companyRepository = companyRepository;
        this.couponRepository = couponRepository;
        this.customerRepository = customerRepository;
        this.loginManager = loginManager;
    }
    public abstract Token Login(LoginData loginData) throws ClientDoesNotExistException, ClientIdDoesNotExistException;
}
