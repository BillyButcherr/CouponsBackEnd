package com.webApp.controllers;

import com.webApp.LoginData;
import com.webApp.LoginManager;
import com.webApp.Token;
import com.webApp.TokenManager;
import com.webApp.domain.Company;
import com.webApp.domain.Coupon;
import com.webApp.enums.ClientType;
import com.webApp.exceptions.TokenExpiredException;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.client.ClientIdDoesNotExistException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.ClientPermissionDeniedException;
import com.webApp.exceptions.company.CompanyCouponSameTitleAlreadyExistsException;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.coupon.CouponCompanyChangeNotAllowedException;
import com.webApp.exceptions.coupon.CouponDoesNotExistException;
import com.webApp.exceptions.coupon.EmptyCouponIDException;
import com.webApp.facades.CompanyFacade;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController extends ClientController {
    @Autowired
    private TokenManager tokenManager;
    private CompanyFacade companyFacade;
    public CompanyController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager) {
        super(companyRepository, couponRepository, customerRepository, loginManager);
    }

    @Override
    @PostMapping("/login/company")
    public Token Login(@RequestBody LoginData loginData) throws ClientDoesNotExistException, ClientIdDoesNotExistException {
        companyFacade = (CompanyFacade)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Company);
        if(companyFacade == null)
            throw new ClientDoesNotExistException(ClientType.Company);
        else return tokenManager.createCompanyToken(companyFacade.getCompanyID());
    }

    @PostMapping("/companies/{companyId}/coupons")
    public String addCoupon(@PathVariable Long companyId, @RequestBody Coupon coupon, @RequestParam String token) throws CompanyCouponSameTitleAlreadyExistsException,
            ClientNotLoggedException, ClientPermissionDeniedException, TokenExpiredException {
        isCompanyLogged(companyId, new Token(token));
        companyFacade.addCoupon(coupon);
        return "Added new coupon " + coupon;
    }
    @PutMapping("/companies/{companyId}/coupons/{couponId}")
    public String updateCoupon(@PathVariable Long companyId, @PathVariable Long couponId, @RequestBody Coupon coupon, @RequestParam String token) throws EmptyCouponIDException,
            CouponDoesNotExistException, CouponCompanyChangeNotAllowedException, ClientNotLoggedException, ClientPermissionDeniedException, TokenExpiredException {
        isCompanyLogged(companyId, new Token(token));
        companyFacade.updateCoupon(coupon);
        return "Updated a coupon " + coupon;
    }
    @DeleteMapping("/companies/{companyId}/coupons/{couponId}")
    public String deleteCoupon(@PathVariable Long companyId, @PathVariable Long couponId, @RequestParam String token) throws EmptyCouponIDException,
            CouponDoesNotExistException, ClientNotLoggedException, ClientPermissionDeniedException, TokenExpiredException {
        isCompanyLogged(companyId, new Token(token));
        companyFacade.deleteCoupon(couponId);
        return "Deleted a coupon " + couponId;
    }
    @GetMapping("/companies/{companyId}/coupons")
    public List<Coupon> getAllCoupons(@PathVariable Long companyId, @RequestParam String token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, TokenExpiredException {
        isCompanyLogged(companyId, new Token(token));
        return companyFacade.getAllCompanyCoupons();
    }
    @GetMapping("/companies/{companyId}/coupons/{couponId}")
    public Coupon getCoupon(@PathVariable Long companyId, @PathVariable Long couponId, @RequestParam String token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, CouponDoesNotExistException, TokenExpiredException {
        isCompanyLogged(companyId, new Token(token));
        return companyFacade.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getId() == couponId)
                .findFirst()
                .orElseThrow(CouponDoesNotExistException::new);
    }
    @GetMapping("/companies/{companyId}/info")
    public Company getCompanyDetails(@PathVariable Long companyId, @RequestParam String token) throws CompanyDoesNotExistException,
            ClientNotLoggedException, ClientPermissionDeniedException, TokenExpiredException {
        isCompanyLogged(companyId, new Token(token));
        return companyFacade.getCompanyDetails();
    }
    private void isCompanyLogged(Long requestCompanyId, Token token) throws ClientNotLoggedException,
            ClientPermissionDeniedException, TokenExpiredException {
        if(this.companyFacade == null)
            throw new ClientNotLoggedException(ClientType.Company);
        if(!tokenManager.isValidToken(requestCompanyId, ClientType.Company, token))
            throw new ClientPermissionDeniedException(ClientType.Company, requestCompanyId);
    }
}
