package com.webApp.exceptions.company;

import com.webApp.exceptions.CustomException;

public class CompanyAlreadyExistsException extends CustomException {
    public CompanyAlreadyExistsException() {
        super("A company with the same name or email already exists.");
    }
}
