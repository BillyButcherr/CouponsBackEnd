package com.webApp.services;

import com.webApp.Token;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.TokenExpiredException;
import com.webApp.models.enums.ClientType;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final JwtEncoder jwtEncoder;
    private Token adminToken;
    private Map<Long, Token> companyTokens;
    private Map<Long, Token> customerTokens;

    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.companyTokens = new HashMap<>();
        this.customerTokens = new HashMap<>();
    }

    /**
     * Generates a jwt token valid for 5 hours, and stores the client info in it.
     * @param authentication
     * @param clientID
     * @param clientType
     * @return
     */
    public Token generateToken(Authentication authentication, long clientID, ClientType clientType){
        Instant now = Instant.now();
        Instant exp = now.plus(5, ChronoUnit.HOURS);
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(exp)
                .subject(authentication.getName())
                .claim("scope", authorities)
                .claim("clientId", clientID)
                .build();
        Token token = new Token(this.jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue(),
                now, exp, clientID);
        addToTokensMap(token, clientType);
        return token;
    }

    /**
     * stores the token at a specific map.
     * @param token
     * @param clientType
     */
    private void addToTokensMap(Token token, ClientType clientType){
        switch (clientType){
            case Administrator:{
                this.adminToken = token;
            }break;
            case Company:{
                if(this.companyTokens == null)
                    this.companyTokens = new HashMap<>();
                this.companyTokens.put(token.getClientId(), token);
            }break;
            case Customer:{
                if(this.customerTokens == null)
                    this.customerTokens = new HashMap<>();
                this.customerTokens.put(token.getClientId(), token);
            }break;
        }
    }
    public Token getAdminToken() {
        return adminToken;
    }
    public void setAdminToken(Token adminToken) {
        this.adminToken = adminToken;
    }
    public Map<Long, Token> getCompanyTokens() {
        return companyTokens;
    }
    public Map<Long, Token> getCustomerTokens() {
        return customerTokens;
    }

    /**
     *
     * @param clientId
     * @param clientType
     * @return the token of a specific client.
     * @throws ClientNotLoggedException
     */
    private Token getTokenElseThrow(long clientId, ClientType clientType) throws ClientNotLoggedException {
        Token token = null;
        switch (clientType){
            case Administrator:{
                token = this.adminToken;
            }break;
            case Company:{
                token = this.companyTokens.get(clientId);
            }break;
            case Customer:{
                token = this.customerTokens.get(clientId);
            }break;
        }
        if(token == null)
            throw new ClientNotLoggedException();
        return token;
    }

    /**
     * Stores the last time an activity with a specific token was made.
     * @param clientId
     * @param clientType
     * @throws ClientNotLoggedException
     */
    public void updateLastActivity(long clientId, ClientType clientType) throws ClientNotLoggedException {
        Token token = getTokenElseThrow(clientId, clientType);
        token.updateLastActivity();
    }

    /**
     * Checks whether a token expired(30 min last activity or 5 hours life cycle).
     * @param clientId
     * @param clientType
     * @return
     * @throws ClientNotLoggedException
     */
    public boolean hasTokenExpired(long clientId, ClientType clientType) throws ClientNotLoggedException {
        Token token = getTokenElseThrow(clientId, clientType);
        return token.hasExpired();
    }
}
