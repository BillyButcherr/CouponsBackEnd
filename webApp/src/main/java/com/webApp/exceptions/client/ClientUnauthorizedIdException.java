package com.webApp.exceptions.client;

import com.webApp.exceptions.CustomException;
import com.webApp.models.enums.ClientType;

public class ClientUnauthorizedIdException extends CustomException {
    public ClientUnauthorizedIdException(ClientType clientType, long clientId) {
        super("Unauthorized action,  must be logged in with the same " + clientType + " id : " + clientId);
    }
}
