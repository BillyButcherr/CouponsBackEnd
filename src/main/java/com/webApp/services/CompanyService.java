package com.webApp.services;

import com.webApp.exceptions.client.ClientUnauthorizedIdException;
import com.webApp.exceptions.coupon.CouponUnauthorizedAccessException;
import com.webApp.models.*;
import com.webApp.exceptions.client.ClientDoesNotExistException;
import com.webApp.exceptions.company.CompanyCouponSameTitleAlreadyExistsException;
import com.webApp.exceptions.company.CompanyDoesNotExistException;
import com.webApp.exceptions.coupon.CouponCompanyChangeNotAllowedException;
import com.webApp.exceptions.coupon.CouponDoesNotExistException;
import com.webApp.exceptions.coupon.EmptyCouponIDException;
import com.webApp.models.enums.Category;
import com.webApp.models.enums.ClientType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service//prototype
public class CompanyService extends ClientService {
    /**
     *
     * @param coupon The coupon to be added.
     * @return The added coupon generated id.
     * @throws CompanyCouponSameTitleAlreadyExistsException
     */
    public long addCoupon(long companyId, Coupon coupon) throws CompanyCouponSameTitleAlreadyExistsException {
        List<Coupon> allCoupons = couponRepository.findAll();
        boolean couponWithSameTitle = false;
        if(allCoupons.size() > 0){
            couponWithSameTitle = allCoupons.stream()
                    .anyMatch(otherCoupon -> otherCoupon.getCompany().getId() == coupon.getCompany().getId() &&
                            otherCoupon.getTitle().equals(coupon.getTitle()));
        }
        if(couponWithSameTitle)
            throw new CompanyCouponSameTitleAlreadyExistsException();
        return couponRepository.save(coupon).getId();
    }
    /**
     *
     * @param coupon The coupon to be updated.
     * @throws EmptyCouponIDException ID field inside coupon hasn't been initialized yet.
     * @throws CouponDoesNotExistException
     * @throws CouponCompanyChangeNotAllowedException
     */
    public void updateCoupon(long companyId, Coupon coupon) throws EmptyCouponIDException, CouponDoesNotExistException,
            CouponCompanyChangeNotAllowedException {
        if(coupon.getId() == 0)
            throw new EmptyCouponIDException();
        Coupon oldRecord = couponRepository.findById(coupon.getId())
                .orElseThrow(CouponDoesNotExistException::new);
        if(coupon.getCompany().getId() != oldRecord.getCompany().getId())
            throw new CouponCompanyChangeNotAllowedException();
        couponRepository.save(coupon);
    }

    public void deleteCoupon(long companyId, long couponID) throws EmptyCouponIDException, CouponDoesNotExistException, CouponUnauthorizedAccessException {
        if(couponID == 0)
            throw new EmptyCouponIDException();
        Coupon oldCoupon = couponRepository.findById(couponID)
                .orElseThrow(CouponDoesNotExistException::new);
        if(companyId != oldCoupon.getCompany().getId())
            throw new CouponUnauthorizedAccessException();
        couponRepository.deleteAllCouponPurchases(couponID);
        couponRepository.deleteById(couponID);
    }

    public Set<Coupon> getAllCompanyCoupons(long companyID) throws CompanyDoesNotExistException {
        return couponRepository.getAllCompanyCoupons(companyID);
    }
    public Set<Coupon> getAllCompanyCoupons(long companyId, Category category) throws CompanyDoesNotExistException {
        return this.getAllCompanyCoupons(companyId).stream()
                .filter(coupon -> coupon.getCategory().equals(category))
                .collect(Collectors.toSet());
    }
    public Set<Coupon> getAllCompanyCoupons(long companyId, double maxPrice) throws CompanyDoesNotExistException {
        return this.getAllCompanyCoupons(companyId).stream()
                .filter(coupon -> coupon.getPrice() <= maxPrice)
                .collect(Collectors.toSet());
    }
    public Coupon getOneCompanyCoupon(long couponId) throws CouponDoesNotExistException {
        return couponRepository.findById(couponId).orElseThrow(CouponDoesNotExistException::new);
    }
    public Company getCompanyDetails(long companyId) throws CompanyDoesNotExistException {
        return companyRepository.findById(companyId).orElseThrow(CompanyDoesNotExistException::new);
    }
}
