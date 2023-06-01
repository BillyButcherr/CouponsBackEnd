package com.webApp.security;

import com.webApp.models.Company;
import com.webApp.models.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser implements UserDetails {
    private final String username;
    private final String password;
    private ArrayList<GrantedAuthority> authorities;

    public SecurityUser(Customer customer) {
        this.username = customer.getEmail();
        this.password = customer.getPassword();

        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }
    public SecurityUser(Company company) {
        this.username = company.getEmail();
        this.password = company.getPassword();

        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY"));
    }
    public SecurityUser(boolean isAdmin) {
        this.username = "admin@admin.com";
        this.password = "{bcrypt}" + new BCryptPasswordEncoder(10).encode("admin");

        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
