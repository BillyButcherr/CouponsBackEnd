package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class CouponZeroAmountException extends CustomException {
    public CouponZeroAmountException() {
        super("Coupon amount is zero");
    }
}
