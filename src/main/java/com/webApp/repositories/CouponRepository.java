package com.webApp.repositories;

import com.webApp.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value = "select * from coupons inner join customers_coupons on id=coupon_id and customer_id=:customerID", nativeQuery = true)
    Set<Coupon> getAllCustomerCoupons(@Param("customerID") long customerID);

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where coupon_id=:couponID", nativeQuery = true)
    void deleteAllCouponPurchases(@Param("couponID") long couponID);

    @Transactional
    @Modifying
    @Query(value = "delete customers_coupons from customers_coupons inner join coupons on coupon_id=id where company_id=:companyID", nativeQuery = true)
    void deleteAllCompanyCouponsPurchases(@Param("companyID") long companyID);

    @Transactional
    @Modifying
    @Query(value = "delete from coupons where company_id=:companyID", nativeQuery = true)
    void deleteAllCompanyCoupons(@Param("companyID") long companyID);

    @Transactional
    @Modifying
    @Query(value = "delete from customers_coupons where customer_id=:customerID", nativeQuery = true)
    void deleteAllCustomerCouponsPurchases(@Param("customerID") long customerID);

    @Transactional
    @Modifying
    @Query(value = "insert into customers_coupons values(?,?)", nativeQuery = true)
    void addCustomerPurchase(long customerId, long couponId);

    @Query(value = "select * from coupons where company_id=:companyID", nativeQuery = true)
    Set<Coupon> getAllCompanyCoupons(@Param("companyID") long companyID);

    //@Query(value = "insert into customers_vs_coupons values(?,?)", nativeQuery = true)
    //@Query(value = "insert into customers_coupons values(:customerID,:couponID)", nativeQuery = true)
    //void addCouponPurchase(@Param("customerID")long customerID, @Param("couponID")long couponID);
    //
    //List<Coupon> findByCompanyID(long companyID);


}
