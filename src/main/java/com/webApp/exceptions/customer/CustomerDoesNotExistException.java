package com.webApp.exceptions.customer;

import com.webApp.exceptions.CustomException;

public class CustomerDoesNotExistException extends CustomException {
    public CustomerDoesNotExistException() {
        super("A customer with that id doesn't exist.");
    }
}
