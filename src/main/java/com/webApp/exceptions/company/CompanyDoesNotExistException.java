package com.webApp.exceptions.company;

import com.webApp.exceptions.CustomException;

public class CompanyDoesNotExistException extends CustomException {
    public CompanyDoesNotExistException() {
        super("A company with that id doesn't exist.");
    }
}
