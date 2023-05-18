package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class CouponDateExpiredException extends CustomException {
    public CouponDateExpiredException() {
        super("Coupon date has expired.");
    }
}
