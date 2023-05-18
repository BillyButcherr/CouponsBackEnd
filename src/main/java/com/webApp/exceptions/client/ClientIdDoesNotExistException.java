package com.webApp.exceptions.client;

import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;

public class ClientIdDoesNotExistException extends CustomException {
    public ClientIdDoesNotExistException(ClientType clientType) {
        super("Could not find a " + clientType + " with a matching ID.");
    }
}
