package com.webApp.exceptions.customer;

import com.webApp.exceptions.CustomException;

public class CustomerAlreadyExistsException extends CustomException {
    public CustomerAlreadyExistsException() {
        super("A customer with the same email already exists.");
    }
}
