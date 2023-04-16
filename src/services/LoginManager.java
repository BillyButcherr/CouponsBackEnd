package com.webApp.services;

import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    @Autowired
    ConfigurableApplicationContext context;
    public ClientService Login(String email, String password, ClientType clientType) throws ClientDoesNotExistException {
        ClientService clientService = null;
        switch (clientType){
            case Administrator:{clientService = context.getBean(AdminService.class);}break;
            case Company:{clientService = context.getBean(CompanyService.class);}break;
            case Customer:{clientService = context.getBean(CustomerService.class);}break;
        }
        if(clientService.login(email, password)){
            return clientService;
        } else { // to make sure clientFacade with empty or -1 id is never used in future versions of code
            throw new ClientDoesNotExistException(clientType);
        }
    }
}
