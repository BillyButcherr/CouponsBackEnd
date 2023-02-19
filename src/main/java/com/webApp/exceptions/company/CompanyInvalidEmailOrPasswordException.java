package com.webApp.exceptions.company;

import com.webApp.exceptions.CustomException;

public class CompanyInvalidEmailOrPasswordException extends CustomException {
    public CompanyInvalidEmailOrPasswordException() {
        super("Company invalid email or password at login.");
    }
}
