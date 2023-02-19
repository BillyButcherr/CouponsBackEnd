package com.webApp.exceptions.client;

import com.webApp.enums.ClientType;
import com.webApp.exceptions.CustomException;

public class ClientNotLoggedException extends CustomException {
    public ClientNotLoggedException(ClientType clientType) {
        super(clientType + " must be logged in.");
    }
}
