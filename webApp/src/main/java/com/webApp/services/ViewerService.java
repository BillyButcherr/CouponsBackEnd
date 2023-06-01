package com.webApp.services;

import com.webApp.models.Coupon;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.coupon.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewerService extends ClientService {
    /**
     *
     * @return a list of all coupons.
     */
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}
