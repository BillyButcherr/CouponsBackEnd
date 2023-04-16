import { useEffect, useState } from "react";
import Coupon from "../Coupon/Coupon";
import "./CouponContainer.css";
import CouponItem from "../../../../Models/Coupon/CouponItem";
import CouponService from "../../../../Services/ViewerService";
import { CouponsStore, fetchCoupons } from "../../../../Store/CouponsState";

function CouponContainer(): JSX.Element {
    const [coupons, setCoupons] = useState<CouponItem[]>([]);

    useEffect(() => {
        if (CouponsStore.getState().coupons.length > 0) {
            setCoupons(CouponsStore.getState().coupons);
        } else {
            CouponService.getAllCoupons().then(coupons => {
                CouponsStore.dispatch(fetchCoupons(coupons));
                setCoupons(coupons);
            }).catch(err => console.log(err));
        }

    }, []);
    return (
        <div className="coupon-container">
            {coupons.map((coupon) => (
                <Coupon key={coupon.id} info={coupon}/>
            ))}
        </div>
    );
}

export default CouponContainer;

