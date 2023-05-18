package com.webApp.services;

import com.webApp.models.*;
import com.webApp.exceptions.coupon.*;
import com.webApp.exceptions.customer.CustomerDoesNotExistException;
import com.webApp.exceptions.customer.CustomerPurchasedCouponAlreadyException;
import com.webApp.exceptions.customer.EmptyCustomerIDException;
import com.webApp.models.enums.Category;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerService extends ClientService {
    //Works
    public void purchaseCoupon(long customerId, long couponId) throws EmptyCouponIDException, CouponZeroAmountException, CustomerPurchasedCouponAlreadyException,
            CouponDateExpiredException, CouponDateAheadOfTimeException, CouponDoesNotExistException {
        if (couponId == 0)
            throw new EmptyCouponIDException();
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(CouponDoesNotExistException::new);
        if(coupon.getAmount() <= 0)
            throw new CouponZeroAmountException();
        boolean existingCoupon = couponRepository.getAllCustomerCoupons(customerId).stream()
                .anyMatch(otherCoupon -> otherCoupon.getId() == coupon.getId());
        if(existingCoupon)
            throw new CustomerPurchasedCouponAlreadyException();
        validDateElseThrow(coupon);

        coupon.reduceAmountByOne();
        couponRepository.save(coupon);
        couponRepository.addCustomerPurchase(customerId, couponId);
    }
    private void validDateElseThrow(Coupon coupon) throws CouponDateExpiredException, CouponDateAheadOfTimeException {
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
    public Set<Coupon> getCustomerPurchasedCoupons(long customerId){
        return couponRepository.getAllCustomerCoupons(customerId);
    }
    //Works
    public Set<Coupon> getCustomerPurchasedCoupons(long customerId, Category category){
        return getCustomerPurchasedCoupons(customerId).stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toSet());
    }
    //Works
    public Set<Coupon> getCustomerPurchasedCoupons(long customerId, double maxPrice){
        return getCustomerPurchasedCoupons(customerId).stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toSet());
    }
    //Works
    public Customer getCustomerDetails(long customerId) throws EmptyCustomerIDException, CustomerDoesNotExistException {
//        if (customerId == 0)
//            throw new EmptyCustomerIDException();
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(CustomerDoesNotExistException::new);
        customer.setCoupons(couponRepository.getAllCustomerCoupons(customerId));
        return customer;
    }
}
