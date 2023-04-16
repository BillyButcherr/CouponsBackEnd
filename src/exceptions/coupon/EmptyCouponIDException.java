package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class EmptyCouponIDException extends CustomException {
    public EmptyCouponIDException() {
        super("Empty ID field inside coupon");
    }
}
