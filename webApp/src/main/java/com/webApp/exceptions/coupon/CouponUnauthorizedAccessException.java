package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class CouponUnauthorizedAccessException extends CustomException {
    public CouponUnauthorizedAccessException() {
        super("Accessing a coupon of a different company is forbidden.");
    }
}
