package com.webApp.exceptions.client;

import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;

public class ClientNotLoggedException extends CustomException {
    public ClientNotLoggedException() {
        super("Client must be logged in.");
    }
    public ClientNotLoggedException(ClientType clientType) {
        super(clientType + " must be logged in.");
    }
}
