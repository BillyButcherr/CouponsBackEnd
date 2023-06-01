package com.webApp.exceptions.client;

import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.CustomException;

public class ClientPermissionDeniedException extends CustomException {
    public ClientPermissionDeniedException(ClientType clientType, Long id) {
        super("Permission denied, " + clientType + " must be logged in as " + id);
    }
}
