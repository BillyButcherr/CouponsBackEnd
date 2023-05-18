package com.webApp.exceptions.customer;

import com.webApp.exceptions.CustomException;

public class EmptyCustomerIDException extends CustomException {
    public EmptyCustomerIDException() {
        super("Empty ID field inside customer");
    }
}
