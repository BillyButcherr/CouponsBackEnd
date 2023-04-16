package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class CouponDateAheadOfTimeException extends CustomException {
    public CouponDateAheadOfTimeException() {
        super("Coupon start date is ahead of time.");
    }
}
