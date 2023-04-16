package com.webApp.services;

import com.webApp.models.Coupon;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.coupon.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewerService extends ClientService {

    @Override
    public boolean login(String email, String password) throws ClientDoesNotExistException {
        return false;
    }
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
    public Coupon getCoupon(long couponID) throws CouponDoesNotExistException {
        return couponRepository.findById(couponID).orElseThrow(CouponDoesNotExistException::new);
    }
}
