package com.webApp.services;

import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CustomerRepository;
import com.webApp.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    public JpaUserDetailsService(CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin@admin.com"))
            return new SecurityUser(true);
        UserDetails userDetails = companyRepository.findByEmail(username).map(SecurityUser::new).orElse(null);
        if(userDetails != null)
            return userDetails;
        userDetails = customerRepository.findByEmail(username).map(SecurityUser::new).orElse(null);
        if(userDetails != null)
            return userDetails;
        throw new UsernameNotFoundException("Username not found: " + username);
    }
}
