package com.webApp.exceptions.customer;

import com.webApp.exceptions.CustomException;

public class CustomerPurchasedCouponAlreadyException extends CustomException {
    public CustomerPurchasedCouponAlreadyException() {
        super("the same coupon was purchased by this customer already.");
    }
}
