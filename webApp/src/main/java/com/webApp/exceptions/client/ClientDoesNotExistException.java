package com.webApp.exceptions.client;

import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;

public class ClientDoesNotExistException extends CustomException {
    public ClientDoesNotExistException(ClientType clientType) {
        super("Could not find a " + clientType + " with matching email and password.");
    }
}
