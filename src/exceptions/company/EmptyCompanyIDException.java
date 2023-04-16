package com.webApp.exceptions.company;

import com.webApp.exceptions.CustomException;

public class EmptyCompanyIDException extends CustomException {
    public EmptyCompanyIDException() {
        super("Empty ID field inside company");
    }
}
