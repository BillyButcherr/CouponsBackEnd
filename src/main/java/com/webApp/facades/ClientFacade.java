package com.webApp.facades;

import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientFacade {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CouponRepository couponRepository;
    public abstract boolean login(String email, String password) throws ClientDoesNotExistException;
}
