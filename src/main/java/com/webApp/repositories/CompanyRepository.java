package com.webApp.repositories;

import com.webApp.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);

    Optional<Company> findByEmailAndPassword(String email, String password);
}
