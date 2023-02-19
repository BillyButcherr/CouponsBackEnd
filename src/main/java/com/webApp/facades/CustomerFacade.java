package com.webApp.facades;

import com.webApp.domain.*;
import com.webApp.enums.*;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.coupon.*;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.CustomerPurchasedCouponAlreadyException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerFacade extends ClientFacade{
    private long customerID;
    private Customer customerInstance;
    @Override
    public boolean login(String email, String password) throws ClientDoesNotExistException {
        Customer customer = customerRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ClientDoesNotExistException(ClientType.Customer));
        customerID = customer.getId();
        this.customerInstance = customer;
        return customerID != 0;
    }
    //Works
    public void purchaseCoupon(Coupon coupon) throws EmptyCouponIDException, CouponZeroAmountException, CustomerPurchasedCouponAlreadyException,
            CouponDateExpiredException, CouponDateAheadOfTimeException {
        if (coupon.getId() == 0)
            throw new EmptyCouponIDException();
        if(coupon.getAmount() <= 0)
            throw new CouponZeroAmountException();
        boolean existingCoupon = couponRepository.getAllCustomerCoupons(customerID).stream()
                .anyMatch(couponItem -> couponItem.getId() == coupon.getId());
        if(existingCoupon)
            throw new CustomerPurchasedCouponAlreadyException();
        validateDateOrThrow(coupon);

        coupon.reduceAmountByOne();
        this.customerInstance.addToCouponsList(coupon);
        couponRepository.save(coupon);
        //removeDuplicatesFromCouponsList();
        customerRepository.save(customerInstance);
        this.customerInstance.getCoupons().remove(coupon);
    }
    private void validateDateOrThrow(Coupon coupon) throws CouponDateExpiredException, CouponDateAheadOfTimeException {
        Calendar today = Calendar.getInstance(), startDate = Calendar.getInstance(), endDate = Calendar.getInstance();
        startDate.setTime(coupon.getStartDate());
        endDate.setTime(coupon.getEndDate());
        if(today.after(endDate))
            throw new CouponDateExpiredException();
        if(today.before(startDate))
            throw new CouponDateAheadOfTimeException();
    }
    private void removeDuplicatesFromCouponsList(){
        List<Coupon> customerCoupons = this.getCustomerCoupons();
        for (Coupon coupon: this.customerInstance.getCoupons()) {
            for (Coupon fetchedCoupon: customerCoupons) {
                if (coupon.getId() == fetchedCoupon.getId())
                    this.customerInstance.getCoupons().remove(coupon);
            }
        }
    }
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
    public Coupon getCoupon(long couponID) throws CouponDoesNotExistException {
        return couponRepository.findById(couponID).orElseThrow(CouponDoesNotExistException::new);
    }

    //Works
    public List<Coupon> getCustomerCoupons(){
        return couponRepository.getAllCustomerCoupons(customerID);
    }
    //Works
    public ArrayList<Coupon> getCustomerCoupons(Category category){
        return (ArrayList<Coupon>)couponRepository.getAllCustomerCoupons(customerID).stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    //Works
    public ArrayList<Coupon> getCustomerCoupons(double maxPrice){
        return (ArrayList<Coupon>)couponRepository.getAllCustomerCoupons(customerID).stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    //Works
    public Customer getCustomerDetails() throws EmptyCustomerIDException, CustomerDoesNotExistException {
        if (customerID == 0)
            throw new EmptyCustomerIDException();
        Customer customer = customerRepository.findById(customerID)
                .orElseThrow(CustomerDoesNotExistException::new);
        customer.setCoupons(couponRepository.getAllCustomerCoupons(customerID));
        return customer;
    }

    public long getCustomerID() {
        return customerID;
    }
}
