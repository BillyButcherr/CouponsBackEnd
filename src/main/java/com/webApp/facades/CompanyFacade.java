package com.webApp.facades;

import com.webApp.domain.*;
import com.webApp.enums.*;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.company.CompanyCouponSameTitleAlreadyExistsException;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.coupon.CouponCompanyChangeNotAllowedException;
import com.webApp.exceptions.coupon.CouponDoesNotExistException;
import com.webApp.exceptions.coupon.EmptyCouponIDException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyFacade extends ClientFacade{
    private long companyID;

    @Override
    public boolean login(String email, String password) throws ClientDoesNotExistException {
        companyID = companyRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new ClientDoesNotExistException(ClientType.Company)).getId();
        return companyID != 0;
    }

    /**
     *
     * @param coupon The coupon to be added.
     * @return The added coupon generated id.
     * @throws CompanyCouponSameTitleAlreadyExistsException
     */
    public long addCoupon(Coupon coupon) throws CompanyCouponSameTitleAlreadyExistsException {
        List<Coupon> allCoupons = couponRepository.findAll();
        boolean couponWithSameTitle = false;
        if(allCoupons.size() > 0){
            couponWithSameTitle = allCoupons.stream()
                    .anyMatch(otherCoupon -> otherCoupon.getCompanyID() == coupon.getCompanyID() &&
                            otherCoupon.getTitle().equals(coupon.getTitle()));
        }
        if(couponWithSameTitle)
            throw new CompanyCouponSameTitleAlreadyExistsException();
        else return couponRepository.save(coupon).getId();
    }

    /**
     *
     * @param coupon The coupon to be updated.
     * @throws EmptyCouponIDException ID field inside coupon hasn't been initialized yet.
     * @throws CouponDoesNotExistException
     * @throws CouponCompanyChangeNotAllowedException
     */
    public void updateCoupon(Coupon coupon) throws EmptyCouponIDException, CouponDoesNotExistException,
            CouponCompanyChangeNotAllowedException {
        if(coupon.getId() == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponRepository.findById(coupon.getId())
                .orElseThrow(CouponDoesNotExistException::new);
        if(coupon.getCompanyID() != oldRecord.getCompanyID())
            throw new CouponCompanyChangeNotAllowedException();
        couponRepository.save(coupon);
    }

    public void deleteCoupon(long couponID) throws EmptyCouponIDException, CouponDoesNotExistException {
        if(couponID == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponRepository.findById(couponID)
                .orElseThrow(CouponDoesNotExistException::new);
        //couponsDAO.deleteAllCouponsPurchases(coupon); cascade should take care of this after the coupon is deleted below
        couponRepository.deleteById(couponID);
        //couponsDAO.deleteCoupon(coupon);
    }

    public ArrayList<Coupon> getAllCompanyCoupons(){
        return (ArrayList<Coupon>) couponRepository.findAll().stream()
                .filter(coupon -> coupon.getCompanyID() == this.companyID)
                .collect(Collectors.toList());
    }
    public ArrayList<Coupon> getAllCompanyCoupons(Category category){
        return (ArrayList<Coupon>) this.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    public ArrayList<Coupon> getAllCompanyCoupons(double maxPrice){
        return (ArrayList<Coupon>) this.getAllCompanyCoupons().stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
    public Company getCompanyDetails() throws CompanyDoesNotExistException {
        return companyRepository.findById(this.companyID).orElseThrow(CompanyDoesNotExistException::new);
    }

    public long getCompanyID() {
        return companyID;
    }
}
