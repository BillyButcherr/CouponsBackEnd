package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class CouponDoesNotExistException extends CustomException {
    public CouponDoesNotExistException() {
        super("A coupon with that id doesn't exist.");
    }
}
