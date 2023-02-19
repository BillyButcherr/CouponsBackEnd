package com.webApp.facades;

import com.webApp.domain.*;
import com.webApp.exceptions.company.CompanyAlreadyExistsException;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.company.CompanyNameChangeNotAllowedException;
import com.webApp.exceptions.company.EmptyCompanyIDException;
import com.webApp.exceptions.customer.CustomerAlreadyExistsException;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminFacade extends ClientFacade{
    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    //Works
    public long addCompany(Company company) throws SQLException, CompanyAlreadyExistsException {
        boolean companyExists = companyRepository.findAll().stream()
                .anyMatch(company1 -> company1.getName().equals(company.getName()) || company1.getEmail().equals(company.getEmail()));
        if (companyExists)
            throw new CompanyAlreadyExistsException();
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


    public void deleteCompany(long companyID) throws SQLException, EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        if(!companyRepository.existsById(companyID))
            throw new CompanyDoesNotExistException();
        //couponsDAO.deleteAllCompanyCouponsPurchases(company);
        List<Coupon> companyCouponList = couponRepository.findByCompanyID(companyID);
        List<Long> couponsToDelete = companyCouponList.stream().map(Coupon::getId)
                .collect(Collectors.toList());
//        if (!couponsToDelete.isEmpty())
//            customersCouponsRepository.deleteAllByCouponIdInBatch(couponsToDelete);
        //System.out.println(couponsToDelete);
        //couponsDAO.deleteAllCompanyCoupons(company); cascade effect
        companyRepository.deleteById(companyID);
    }

    //Works
    public List<Company> getAllCompanies(){
       // List<Coupon> allCoupons = couponRepository.findAll();
        List<Company> allCompanies = companyRepository.findAll();
//        for (Company company:allCompanies) {
//            List<Coupon> companyCoupons = allCoupons.stream()
//                    .filter(coupon -> coupon.getCompanyID() == company.getId())
//                    .collect(Collectors.toList());
//            company.setCoupons(companyCoupons);
//        }
        return allCompanies;
    }
    //Works
    public Company getOneCompany(long companyID) throws SQLException, EmptyCompanyIDException, CompanyDoesNotExistException {
        if(companyID == 0)
            throw new EmptyCompanyIDException();
        Company company = companyRepository.findById(companyID)
                .orElseThrow(CompanyDoesNotExistException::new);
        List<Coupon> companyCoupons = couponRepository.findAll().stream()
                .filter(coupon -> coupon.getCompanyID() == companyID)
                .collect(Collectors.toList());
        company.setCoupons(companyCoupons);
        return company;
    }
    //Works
    public long addCustomer(Customer customer) throws SQLException, CustomerAlreadyExistsException {
        boolean customerExists = customerRepository.findAll().stream()
                .anyMatch(customer1 -> customer1.getEmail().equals(customer.getEmail()));
        if(customerExists)
            throw new CustomerAlreadyExistsException();
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
        //couponsDAO.deleteAllCustomerCouponsPurchases(customer); maybe cascade
        customerRepository.deleteById(customerID);
    }
    //Works
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
//        for (Customer customer:allCustomers) {
//            customer.setCoupons(couponRepository.getAllCustomerCoupons(customer.getId()));
//        }
        //return allCustomers;
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