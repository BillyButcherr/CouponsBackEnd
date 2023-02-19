package com.webApp;

import com.webApp.enums.ClientType;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.facades.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoginManager {
    @Autowired
    ConfigurableApplicationContext context;
    public ClientFacade Login(String email, String password, ClientType clientType) throws ClientDoesNotExistException {
        ClientFacade clientFacade = null;
        switch (clientType){
            case Administrator:{clientFacade = context.getBean(AdminFacade.class);}break;
            case Company:{clientFacade = context.getBean(CompanyFacade.class);}break;
            case Customer:{clientFacade = context.getBean(CustomerFacade.class);}break;
        }
        if(clientFacade.login(email, password)){
            return clientFacade;
        } else { // to make sure clientFacade with empty or -1 id is never used in future versions of code
            clientFacade = null;
        }
        return null;
    }
}
