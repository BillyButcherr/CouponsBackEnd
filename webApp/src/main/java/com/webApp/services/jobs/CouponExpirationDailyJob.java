package com.webApp.services.jobs;

import com.webApp.models.Coupon;
import com.webApp.repositories.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
/**
 * <p>
 *     Checks every 24 hours for expired coupons on the database, and deletes them.
 * </p>
 */
@Service
public class CouponExpirationDailyJob implements Runnable {
    private final CouponRepository couponRepository;
    private volatile boolean quit = false;


    public CouponExpirationDailyJob(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    /**
     * <p>
     *     Checks every 24 hours for expired coupons on the database, and deletes them.
     * </p>
     */
    @Override
    public void run() {
        while (!quit){
            Calendar today = Calendar.getInstance();
            List<Coupon> allCoupons = couponRepository.findAll();
            if (allCoupons.size() > 0) {
                allCoupons.stream()
                        .filter(coupon -> today.getTime().after(coupon.getEndDate()))
                        .forEach(expiredCoupon ->{
                            couponRepository.deleteAllCouponPurchases(expiredCoupon.getId());
                            couponRepository.deleteById(expiredCoupon.getId());
                        } );
            }
            try {
                Thread.sleep(24 * 60 * 60 * 1000);
            } catch (InterruptedException e){
                if(quit)
                    return;
            }
        }
    }

    /**
     * <p>Stops current running thread.</p>
     */
    public void stop(){
        quit = true;
    }
}
