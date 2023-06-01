package com.webApp.services;

import com.webApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CouponRepository couponRepository;
//    public abstract boolean login(String email, String password) throws ClientDoesNotExistException;
}
