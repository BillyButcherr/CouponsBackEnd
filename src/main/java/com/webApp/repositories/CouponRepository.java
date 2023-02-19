package com.webApp.repositories;

import com.webApp.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    //List<Coupon> findByEmailAndPassword(String email, String password);

    //implement :
    //@Query(value = "select * from coupons inner join customers_vs_coupons on id=coupon_id and customer_id=?", nativeQuery = true)
    //@Query(value = "select co from coupons co join co.customers_vs_coupons cu where co.id=cu.coupon_id and customer_id=:cuid")
    //@Query(value = "select cop from coupons cop join customers_vs_coupons cvc on cop.id = cvc.coupon_id and cvc.customer_id=:customerID")
    @Query(value = "select * from coupons inner join customers_coupons on id=coupon_id and customer_id=:customerID", nativeQuery = true)
    List<Coupon> getAllCustomerCoupons(@Param("customerID") long customerID);
    //@Query(value = "insert into customers_vs_coupons values(?,?)", nativeQuery = true)
    //@Query(value = "insert into customers_coupons values(:customerID,:couponID)", nativeQuery = true)
    //void addCouponPurchase(@Param("customerID")long customerID, @Param("couponID")long couponID);
    //
    List<Coupon> findByCompanyID(long companyID);
    //@Query(value = "select * from coupons inner join customers_vs_coupons on id=coupon_id and customer_id=?")
 //   List<Coupon> getAllCustomersCoupons(long customerID);
}
