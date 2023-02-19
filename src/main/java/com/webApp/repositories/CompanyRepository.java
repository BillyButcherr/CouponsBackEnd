package com.webApp.repositories;

import com.webApp.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmailAndPassword(String email, String password);
}
