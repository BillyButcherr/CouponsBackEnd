package com.webApp.controllers;

import com.webApp.LoginData;
import com.webApp.LoginManager;
import com.webApp.Token;
import com.webApp.TokenManager;
import com.webApp.domain.Coupon;
import com.webApp.domain.Customer;
import com.webApp.enums.ClientType;
import com.webApp.exceptions.TokenExpiredException;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.client.ClientIdDoesNotExistException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.ClientPermissionDeniedException;
import com.webApp.exceptions.coupon.*;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.CustomerPurchasedCouponAlreadyException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import com.webApp.facades.CustomerFacade;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class CustomerController extends ClientController{
    @Autowired
    TokenManager tokenManager;
    private CustomerFacade customerFacade;
    public CustomerController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager){
        super(companyRepository, couponRepository, customerRepository, loginManager);
    }

    @Override
    @PostMapping("/login/customer")
    public Token Login(@RequestBody LoginData loginData) throws ClientDoesNotExistException, ClientIdDoesNotExistException {
        customerFacade = (CustomerFacade)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Customer);
        if(customerFacade == null)
            throw new ClientDoesNotExistException(ClientType.Customer);
        else return tokenManager.createCustomerToken(customerFacade.getCustomerID());
    }

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons() throws ClientNotLoggedException {
        //isCustomerLogged();
        return customerFacade.getAllCoupons();
    }
    @GetMapping("/coupons/{id}")
    public Coupon getCoupon(@PathVariable Long id) throws CouponDoesNotExistException{
        //isCustomerLogged();
        return customerFacade.getCoupon(id);
    }
    @PostMapping("/customers/{customerId}/purchases")
    public String purchaseCoupon(@PathVariable Long customerId, @RequestBody Coupon coupon, @RequestParam String token) throws CustomerPurchasedCouponAlreadyException, EmptyCouponIDException,
            CouponDateAheadOfTimeException, CouponDateExpiredException, CouponZeroAmountException, ClientNotLoggedException, ClientPermissionDeniedException, TokenExpiredException {
        isCustomerLogged(customerId, new Token(token));
        customerFacade.purchaseCoupon(coupon);
        return "Customer " + customerId + " purchased a coupon " + coupon.getId();
    }
    @GetMapping("/customers/{customerId}/purchases")
    public List<Coupon> getCustomerCoupons(@PathVariable Long customerId, @RequestParam String token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, TokenExpiredException {
        isCustomerLogged(customerId, new Token(token));
        return customerFacade.getCustomerCoupons();
    }
    @GetMapping("/customers/{customerId}/info")
    public Customer getOneCoupon(@PathVariable Long customerId, @RequestParam String token) throws EmptyCustomerIDException,
            CustomerDoesNotExistException, ClientNotLoggedException, ClientPermissionDeniedException, TokenExpiredException {
        isCustomerLogged(customerId, new Token(token));
        return customerFacade.getCustomerDetails();
    }
    private void isCustomerLogged(Long requestCustomerId, Token token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, TokenExpiredException {
        if(this.customerFacade == null)
            throw new ClientNotLoggedException(ClientType.Customer);
        if(!tokenManager.isValidToken(requestCustomerId, ClientType.Customer, token))
            throw new ClientPermissionDeniedException(ClientType.Customer, requestCustomerId);
    }
}
