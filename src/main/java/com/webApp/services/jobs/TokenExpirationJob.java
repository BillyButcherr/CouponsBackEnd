package com.webApp.services.jobs;

import com.webApp.Token;
import com.webApp.services.AuthService;
import com.webApp.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *     Checks every 30 minutes for expired tokens, and deletes them.
 * </p>
 */
@Service
public class TokenExpirationJob implements Runnable {
    private volatile boolean quit = false;
    private TokenService tokenService;

    public TokenExpirationJob(TokenService tokenService) {
        this.tokenService = tokenService;
    }
    @Override
    public void run() {
        while (!quit){
            Instant now = Instant.now();
            List<Token> companyTokens = (List<Token>) tokenService.getCompanyTokens().values();
            if (companyTokens.size() > 0) {
                companyTokens.stream()
                        .filter(token -> now.isAfter(token.getEndDate()))
                        .forEach(expiredToken -> companyTokens.remove(expiredToken));
            }
            List<Token> customerTokens = (List<Token>) tokenService.getCustomerTokens().values();
            if (customerTokens.size() > 0) {
                customerTokens.stream()
                        .filter(token -> now.isAfter(token.getEndDate()))
                        .forEach(expiredToken -> customerTokens.remove(expiredToken));
            }
            Token adminToken = tokenService.getAdminToken();
            if (now.isAfter(adminToken.getEndDate())){
                tokenService.setAdminToken(null);
            }
            try {
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e){
                if(quit)
                    return;
            }
        }
    }
    /**
     * <p>Stops current running thread.</p>
     */
    public void stop(){
        quit = true;
    }
}
