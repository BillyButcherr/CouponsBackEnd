package com.webApp;

import com.webApp.domain.Company;
import com.webApp.domain.Customer;
import com.webApp.enums.ClientType;
import com.webApp.exceptions.TokenExpiredException;
import com.webApp.exceptions.client.ClientIdDoesNotExistException;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class TokenManager {
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final Map<Token, Customer> tokenCustomerMap;
    private final Map<Token, Company> tokenCompanyMap;
    private final Map<Token, Calendar> tokenExpirationMap;

    private Token adminToken;
    private final int TOKEN_SIZE = 4;
    private final int TOKEN_TIMEOUT_MINUTES = 30;

    public TokenManager(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.tokenCustomerMap = new HashMap<>();
        this.tokenCompanyMap = new HashMap<>();
        this.tokenExpirationMap = new HashMap<>();
        this.adminToken = null;
    }

    public Token createCustomerToken(Long id) throws ClientIdDoesNotExistException {
        Token token = generateToken();
        tokenCustomerMap.put(token, customerRepository.findById(id)
                .orElseThrow(() -> new ClientIdDoesNotExistException(ClientType.Customer)));
        addTokenToExpirationMap(token);
        return token;
    }
    public Token createCompanyToken(Long id) throws ClientIdDoesNotExistException {
        Token token = generateToken();
        tokenCompanyMap.put(token, companyRepository.findById(id)
                .orElseThrow(() -> new ClientIdDoesNotExistException(ClientType.Company)));
        addTokenToExpirationMap(token);
        return token;
    }
    public Token createAdminToken(){
        adminToken = generateToken();
        //addTokenToExpirationMap(token);
        return adminToken;
    }
    public boolean isValidToken(Long id, ClientType clientType, Token token) throws TokenExpiredException {
        switch (clientType){
            case Customer:{
                Customer customer = tokenCustomerMap.get(token);
                if(customer == null)
                    return false;
                if(isTokenExpired(token)){
                    tokenCustomerMap.remove(token);
                    throw new TokenExpiredException();
                }
                if(customer.getId() == id)
                    return true;
            }break;
            case Company:{
                Company company = tokenCompanyMap.get(token);
                if(company == null)
                    return false;
                if(isTokenExpired(token)){
                    tokenCompanyMap.remove(token);
                    throw new TokenExpiredException();
                }
                if(company.getId() == id)
                    return true;
            }break;
            case Administrator:{
                if(adminToken == null)
                    return false;
                if(adminToken.getData().equals(token.getData()))
                    return true;
            }break;
        }
        return false;
    }
    private boolean isTokenExpired(Token token){
        Calendar tokenExpiration = tokenExpirationMap.get(token);
        return Calendar.getInstance().after(tokenExpiration);
    }
    private Token generateToken(){
        Random r = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < TOKEN_SIZE; i++) {
            stringBuilder.append(letters.charAt(r.nextInt(letters.length())));
        }
        return new Token(stringBuilder.toString());
    }
    private void addTokenToExpirationMap(Token token){
        Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.MINUTE, TOKEN_TIMEOUT_MINUTES);
        tokenExpirationMap.put(token, expirationTime);
    }
}
