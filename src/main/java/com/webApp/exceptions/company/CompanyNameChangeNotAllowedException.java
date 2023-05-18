package com.webApp.exceptions.company;

import com.webApp.exceptions.CustomException;

public class CompanyNameChangeNotAllowedException extends CustomException {
    public CompanyNameChangeNotAllowedException() {
        super("Companies are not allowed to change their name");
    }
}
