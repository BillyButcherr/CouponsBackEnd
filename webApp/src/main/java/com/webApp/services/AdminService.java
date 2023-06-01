package com.webApp.services;

import com.webApp.models.*;
import com.webApp.exceptions.company.CompanyAlreadyExistsException;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.company.CompanyNameChangeNotAllowedException;
import com.webApp.exceptions.company.EmptyCompanyIDException;
import com.webApp.exceptions.customer.CustomerAlreadyExistsException;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService extends ClientService {
    //Works

    /**
     * Adds company to the database.
     * @param company
     * @return
     * @throws CompanyAlreadyExistsException A company with that name already exists.
     */
    public long addCompany(Company company) throws CompanyAlreadyExistsException {
        boolean companyExists = companyRepository.findAll().stream()
                .anyMatch(aCompany -> aCompany.getName().equals(company.getName()) || aCompany.getEmail().equals(company.getEmail()));
        if (companyExists)
            throw new CompanyAlreadyExistsException();
        company.encodePasswordBeforePersist();
        return companyRepository.save(company).getId();
    }

    /**
     * Updates a given company at the database.
     * @param company the company to be updated.
     * @throws CompanyDoesNotExistException
     * @throws CompanyNameChangeNotAllowedException
     * @throws EmptyCompanyIDException
     */
    public void updateCompany(Company company) throws CompanyDoesNotExistException, CompanyNameChangeNotAllowedException, EmptyCompanyIDException {
        if (company.getId() == 0)
            throw new EmptyCompanyIDException();
        Company oldRecord = companyRepository.findById(company.getId()).orElseThrow(CompanyDoesNotExistException::new);
        if(!oldRecord.getName().equals(company.getName()))
            throw new CompanyNameChangeNotAllowedException();
        companyRepository.save(company);
    }

    /**
     * Updates a given company password at the database.
     * @param company
     * @throws CompanyDoesNotExistException
     * @throws CompanyNameChangeNotAllowedException
     * @throws EmptyCompanyIDException
     */
    public void updateCompanyPassword(Company company) throws CompanyDoesNotExistException, CompanyNameChangeNotAllowedException, EmptyCompanyIDException {
        if (company.getId() == 0)
            throw new EmptyCompanyIDException();
        Company oldRecord = companyRepository.findById(company.getId()).orElseThrow(CompanyDoesNotExistException::new);
        if(!oldRecord.getName().equals(company.getName()))
            throw new CompanyNameChangeNotAllowedException();
        company.encodePasswordBeforePersist();
        companyRepository.save(company);
    }

    /**
     * Deletes a given company from database.
     * @param companyID
     * @throws EmptyCompanyIDException
     * @throws CompanyDoesNotExistException
     */
    public void deleteCompany(long companyID) throws EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        if(!companyRepository.existsById(companyID))
            throw new CompanyDoesNotExistException();
        couponRepository.deleteAllCompanyCouponsPurchases(companyID);
        couponRepository.deleteAllCompanyCoupons(companyID);
        companyRepository.deleteById(companyID);
    }

    /**
     * @return a list of all companies.
     */
    public List<Company> getAllCompaniesLazy(){
        return companyRepository.findAll();
    }

    /**
     *
     * @param companyID
     * @return a company and all its coupons by a given id.
     * @throws EmptyCompanyIDException
     * @throws CompanyDoesNotExistException
     */
    public Company getOneCompany(long companyID) throws EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        Company company = companyRepository.findById(companyID)
                .orElseThrow(CompanyDoesNotExistException::new);
        Set<Coupon> companyCoupons = couponRepository.findAll().stream()
                .filter(coupon -> coupon.getCompany().getId() == companyID)
                .collect(Collectors.toSet());
        company.setCoupons(companyCoupons);
        return company;
    }

    /**
     * Adds customer to the database.
     * @param customer
     * @return
     * @throws CustomerAlreadyExistsException
     */
    public long addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        System.out.println(customer);
        boolean customerExists = customerRepository.findAll().stream()
                .anyMatch(customer1 -> customer1.getEmail().equals(customer.getEmail()));
        if(customerExists)
            throw new CustomerAlreadyExistsException();
        customer.encodePasswordBeforePersist();
        return customerRepository.save(customer).getId();
    }

    /**
     * Updates a given customer at the database.
     * @param customer the customer to be updated.
     * @throws EmptyCustomerIDException
     * @throws CustomerDoesNotExistException
     * @throws CustomerAlreadyExistsException
     */
    public void updateCustomer(Customer customer) throws EmptyCustomerIDException, CustomerDoesNotExistException, CustomerAlreadyExistsException {
        if (customer.getId() == 0)
            throw new EmptyCustomerIDException();
        if(!customerRepository.existsById(customer.getId()))
            throw new CustomerDoesNotExistException();
        boolean otherCustomerWithSameEmailExists = customerRepository.findAll().stream()
                .anyMatch(otherCustomer -> otherCustomer.getId() != customer.getId() && otherCustomer.getEmail().equals(customer.getEmail()));
        if(otherCustomerWithSameEmailExists)
            throw new CustomerAlreadyExistsException();
        customerRepository.save(customer);
    }

    /**
     * Updates a given customer's password at the database,
     * encodes the password before saving to database.
     * @param customer the customer whose password is to be updated.
     * @throws EmptyCustomerIDException
     * @throws CustomerDoesNotExistException
     * @throws CustomerAlreadyExistsException
     */
    public void updateCustomerPassword(Customer customer) throws EmptyCustomerIDException, CustomerDoesNotExistException, CustomerAlreadyExistsException {
        if (customer.getId() == 0)
            throw new EmptyCustomerIDException();
        if(!customerRepository.existsById(customer.getId()))
            throw new CustomerDoesNotExistException();
        boolean otherCustomerWithSameEmailExists = customerRepository.findAll().stream()
                .anyMatch(otherCustomer -> otherCustomer.getId() != customer.getId() && otherCustomer.getEmail().equals(customer.getEmail()));
        if(otherCustomerWithSameEmailExists)
            throw new CustomerAlreadyExistsException();
        customer.encodePasswordBeforePersist();
        customerRepository.save(customer);
    }

    /**
     * Deletes a given customer from database.
     * @param customerID
     * @throws EmptyCustomerIDException
     * @throws CustomerDoesNotExistException
     */
    public void deleteCustomer(long customerID) throws EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        if(!customerRepository.existsById(customerID))
            throw new CustomerDoesNotExistException();
        couponRepository.deleteAllCustomerCouponsPurchases(customerID);
        customerRepository.deleteById(customerID);
    }

    /**
     *
     * @return a list of all customers.
     */
    public List<Customer> getAllCustomersLazy(){
        return customerRepository.findAll();
    }

    /**
     *
     * @param customerID
     * @return a single customer with all of his purchased coupons.
     * @throws EmptyCustomerIDException
     * @throws CustomerDoesNotExistException
     */
    public Customer getOneCustomer(long customerID) throws EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(CustomerDoesNotExistException::new);
        customer.setCoupons(couponRepository.getAllCustomerCoupons(customerID));
        return customer;
    }
}