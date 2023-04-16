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
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    //Works
    public long addCompany(Company company) throws CompanyAlreadyExistsException {
        boolean companyExists = companyRepository.findAll().stream()
                .anyMatch(aCompany -> aCompany.getName().equals(company.getName()) || aCompany.getEmail().equals(company.getEmail()));
        if (companyExists)
            throw new CompanyAlreadyExistsException();
        company.encodePasswordBeforePersist();
        return companyRepository.save(company).getId();
    }
    //Works
    public void updateCompany(Company company) throws CompanyDoesNotExistException, CompanyNameChangeNotAllowedException, EmptyCompanyIDException {
        if (company.getId() == 0)
            throw new EmptyCompanyIDException();
        Company oldRecord = companyRepository.findById(company.getId()).orElseThrow(CompanyDoesNotExistException::new);
        if(!oldRecord.getName().equals(company.getName()))
            throw new CompanyNameChangeNotAllowedException();
        companyRepository.save(company);
    }


    public void deleteCompany(long companyID) throws EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        if(!companyRepository.existsById(companyID))
            throw new CompanyDoesNotExistException();
        couponRepository.deleteAllCompanyCouponsPurchases(companyID);
        couponRepository.deleteAllCompanyCoupons(companyID);
        companyRepository.deleteById(companyID);
    }

    //Works
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    public List<Company> getAllCompaniesLazy(){
        return companyRepository.findAll();
    }
    public List<Company> getAllCompaniesEager(){
        List<Company> allCompanies = companyRepository.findAll();
        List<Coupon> allCoupons = couponRepository.findAll();
        for (Company company:allCompanies) {
            Set<Coupon> companyCoupons = allCoupons.stream()
                    .filter(coupon -> coupon.getCompany().getId() == company.getId())
                    .collect(Collectors.toSet());
            company.setCoupons(companyCoupons);
        }
        return allCompanies;
    }
    //Works
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
    //Works
    public long addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        boolean customerExists = customerRepository.findAll().stream()
                .anyMatch(customer1 -> customer1.getEmail().equals(customer.getEmail()));
        if(customerExists)
            throw new CustomerAlreadyExistsException();
        customer.encodePasswordBeforePersist();
        return customerRepository.save(customer).getId();
    }
    //Works
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


    public void deleteCustomer(long customerID) throws EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        if(!customerRepository.existsById(customerID))
            throw new CustomerDoesNotExistException();
        //customersCouponsRepository.deleteByCustomer_id(customer.getId());
        //couponsDAO.deleteAllCustomerCouponsPurchases(customer);
        couponRepository.deleteAllCustomerCouponsPurchases(customerID);
        customerRepository.deleteById(customerID);
    }
    //Works
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public List<Customer> getAllCustomersLazy(){
        return customerRepository.findAll();
    }
    public List<Customer> getAllCustomersEager(){
        List<Customer> allCustomers =  customerRepository.findAll();
        allCustomers.forEach(customer -> customer.setCoupons(couponRepository.getAllCustomerCoupons(customer.getId())));
        return allCustomers;
    }
    //Works
    public Customer getOneCustomer(long customerID) throws EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(CustomerDoesNotExistException::new);
        customer.setCoupons(couponRepository.getAllCustomerCoupons(customerID));
        return customer;
    }
}