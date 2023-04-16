package com.webApp.exceptions.coupon;

import com.webApp.exceptions.CustomException;

public class CouponCompanyChangeNotAllowedException extends CustomException {
    public CouponCompanyChangeNotAllowedException() {
        super("Coupons are not allowed to change their company id");
    }
}
