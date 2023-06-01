package com.webApp.exceptions.client;

import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;

public class ClientUnauthorizedException extends CustomException {
    public ClientUnauthorizedException(ClientType clientType) {
        super("Unauthorized action,  must be logged in with role" + clientType);
    }
}
