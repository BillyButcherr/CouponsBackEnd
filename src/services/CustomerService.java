package com.webApp.services;

import com.webApp.models.*;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.coupon.*;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.CustomerPurchasedCouponAlreadyException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import com.webApp.models.enums.Category;
import com.webApp.models.enums.ClientType;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService extends ClientService {
    private long customerID;
    private Customer customerInstance;
    @Override
    public boolean login(String email, String password) throws ClientDoesNotExistException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ClientDoesNotExistException(ClientType.Customer));
        customerID = customer.getId();
        this.customerInstance = customer;
        return customerID != 0;
    }
    //Works
    public void purchaseCoupon(long couponId) throws EmptyCouponIDException, CouponZeroAmountException, CustomerPurchasedCouponAlreadyException,
            CouponDateExpiredException, CouponDateAheadOfTimeException, CouponDoesNotExistException {
        Coupon coupon = getCoupon(couponId);
        if (coupon.getId() == 0)
            throw new EmptyCouponIDException();
        if(coupon.getAmount() <= 0)
            throw new CouponZeroAmountException();
        Set<Coupon> customerPurchasedCoupons = getCustomerPurchasedCoupons();
        this.customerInstance.setCoupons(customerPurchasedCoupons);// Lazy init

        boolean existingCoupon = customerPurchasedCoupons.stream()
                .anyMatch(otherPurchasedCoupon -> otherPurchasedCoupon.getId() == coupon.getId());
        if(existingCoupon)
            throw new CustomerPurchasedCouponAlreadyException();
        validateDateOrThrow(coupon);

        coupon.reduceAmountByOne();
        this.customerInstance.addToCouponsList(coupon);
        couponRepository.save(coupon);
        customerRepository.save(customerInstance);
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
    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
    public Coupon getCoupon(long couponID) throws CouponDoesNotExistException {
        return couponRepository.findById(couponID).orElseThrow(CouponDoesNotExistException::new);
    }

    //Works
    public Set<Coupon> getCustomerPurchasedCoupons(){
        return couponRepository.getAllCustomerCoupons(customerID);
    }
    //Works
    public Set<Coupon> getCustomerPurchasedCoupons(Category category){
        return getCustomerPurchasedCoupons().stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toSet());
    }
    //Works
    public Set<Coupon> getCustomerPurchasedCoupons(double maxPrice){
        return getCustomerPurchasedCoupons().stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toSet());
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
