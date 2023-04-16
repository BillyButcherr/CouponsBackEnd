package com.webApp;

import com.webApp.models.Company;
import com.webApp.models.Customer;
import com.webApp.models.enums.ClientType;
import com.webApp.exceptions.client.TokenExpiredException;
import com.webApp.exceptions.client.ClientIdDoesNotExistException;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenManager {
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final Map<Token, Customer> tokenCustomerMap;
    private final Map<Token, Company> tokenCompanyMap;
    private final Map<Token, Calendar> tokenExpirationMap;

    private Token adminToken;
    private final int TOKEN_SIZE = 6;
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
    private boolean isCustomerAuthenticated(Long id, Token token) throws ClientNotLoggedException, TokenExpiredException {
        Customer customer = tokenCustomerMap.get(token);
        if(customer == null)
            throw new ClientNotLoggedException(ClientType.Customer);
        validateToken(token);
        return customer.getId() == id;
    }
    private boolean isCompanyAuthenticated(Long id, Token token) throws TokenExpiredException, ClientNotLoggedException {
        Company company = tokenCompanyMap.get(token);
        if(company == null)
            throw new ClientNotLoggedException(ClientType.Company);
        validateToken(token);
        return company.getId() == id;
    }
    private boolean isAdminAuthenticated(Token token) throws ClientNotLoggedException, TokenExpiredException {
        if(adminToken == null)
            throw new ClientNotLoggedException(ClientType.Administrator);
        if(isTokenExpired(token)){
            tokenCompanyMap.remove(token);
            throw new TokenExpiredException();
        }
        return adminToken.getData().equals(token.getData());
    }
    public boolean isAuthenticated(Long id, ClientType clientType, Token token) throws ClientNotLoggedException, TokenExpiredException {
        switch (clientType){
            case Customer:{return isCustomerAuthenticated(id, token);}
            case Company:{return isCompanyAuthenticated(id, token);}
            case Administrator:{return isAdminAuthenticated(token);}
        }
        return false;
    }
    private void validateToken(Token token) throws TokenExpiredException {
        if(isTokenExpired(token)){
            tokenCompanyMap.remove(token);
            throw new TokenExpiredException();
        }
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
