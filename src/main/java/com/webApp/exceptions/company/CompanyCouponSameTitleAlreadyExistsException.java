package com.webApp.exceptions.company;

import com.webApp.exceptions.CustomException;

public class CompanyCouponSameTitleAlreadyExistsException extends CustomException {
    public CompanyCouponSameTitleAlreadyExistsException() {
        super("A coupon with the same title and companyID already exists.");
    }
}
