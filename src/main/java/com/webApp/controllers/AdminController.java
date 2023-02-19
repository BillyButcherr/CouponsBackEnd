package com.webApp.controllers;

import com.webApp.LoginData;
import com.webApp.LoginManager;
import com.webApp.Token;
import com.webApp.TokenManager;
import com.webApp.domain.Company;
import com.webApp.domain.Customer;
import com.webApp.enums.ClientType;
import com.webApp.exceptions.client.ClientNotLoggedException;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.company.CompanyAlreadyExistsException;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.company.CompanyNameChangeNotAllowedException;
import com.webApp.exceptions.company.EmptyCompanyIDException;
import com.webApp.exceptions.customer.CustomerAlreadyExistsException;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import com.webApp.facades.AdminFacade;
import com.webApp.repositories.CompanyRepository;
import com.webApp.repositories.CouponRepository;
import com.webApp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
public class AdminController extends ClientController{
    @Autowired
    private TokenManager tokenManager;
    private AdminFacade adminFacade;
    public AdminController(CompanyRepository companyRepository, CouponRepository couponRepository, CustomerRepository customerRepository, LoginManager loginManager){
        super(companyRepository, couponRepository, customerRepository, loginManager);
    }

    @Override
    @PostMapping("/login/admin")
    public Token Login(@RequestBody LoginData loginData) throws ClientDoesNotExistException {
        this.adminFacade = (AdminFacade)loginManager.Login(loginData.getEmail(), loginData.getPassword(), ClientType.Administrator);
        if(adminFacade == null)
            throw new ClientDoesNotExistException(ClientType.Administrator);
        else return tokenManager.createAdminToken();
    }

    @PostMapping("/admin/companies")
    public String addCompany(@RequestBody Company company) throws SQLException, CompanyAlreadyExistsException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        long customer1ID = adminFacade.addCompany(company);
        return "Added new company " + company;
    }

    @PutMapping("/admin/companies")
    public String updateCompany(@RequestBody Company company) throws CompanyNameChangeNotAllowedException, CompanyDoesNotExistException, EmptyCompanyIDException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        adminFacade.updateCompany(company);
        return "Updated a customer " + company.getId();
    }
    @DeleteMapping("/admin/companies/{id}")
    public String deleteCompany(@PathVariable Long id) throws CompanyDoesNotExistException, SQLException, EmptyCompanyIDException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        adminFacade.deleteCompany(id);
        return "Deleted a company " + id;
    }
    @GetMapping("/admin/companies")
    public List<Company> getAllCompanies() throws ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        return adminFacade.getAllCompanies();
    }
    @GetMapping("/admin/companies/{id}")
    public Company getCompany(@PathVariable Integer id) throws SQLException, CompanyDoesNotExistException, EmptyCompanyIDException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        return adminFacade.getOneCompany(id);
    }

    @PostMapping("/admin/customers")
    public String addCustomer(@RequestBody Customer customer) throws SQLException, CustomerAlreadyExistsException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        long customer1ID = adminFacade.addCustomer(customer);
        return "Added new customer " + customer;
    }
    @PutMapping("/admin/customers")
    public String updateCustomer(@RequestBody Customer customer) throws EmptyCustomerIDException, CustomerDoesNotExistException,
            CustomerAlreadyExistsException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        adminFacade.updateCustomer(customer);
        return "Updated a customer " + customer.getId();
    }
    @DeleteMapping("/admin/customers/{id}")
    public String deleteCustomer(@PathVariable Long id) throws EmptyCustomerIDException, CustomerDoesNotExistException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        adminFacade.deleteCustomer(id);
        return "Deleted a customer " + id;
    }
    @GetMapping("/admin/customers")
    public List<Customer> getAllCustomers() throws ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        return adminFacade.getAllCustomers();
    }
    @GetMapping("/admin/customers/{id}")
    public Customer getCustomer(@PathVariable Long id) throws EmptyCustomerIDException, CustomerDoesNotExistException, ClientNotLoggedException {
        isAdminLoggedOrElseThrow();
        return adminFacade.getOneCustomer(id);
    }
    private void isAdminLoggedOrElseThrow() throws ClientNotLoggedException {
        if(adminFacade == null)
            throw new ClientNotLoggedException(ClientType.Administrator);
    }
}
