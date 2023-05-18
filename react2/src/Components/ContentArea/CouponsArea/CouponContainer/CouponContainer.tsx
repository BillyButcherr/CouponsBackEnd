import { useEffect, useState } from "react";
import Coupon from "../Coupon/Coupon";
import "./CouponContainer.css";
import CouponItem from "../../../../Models/Coupon/CouponItem";
import viewerService from "../../../../Services/ViewerService";

function CouponContainer(): JSX.Element {
    const [coupons, setCoupons] = useState<CouponItem[]>([]);

    useEffect(() => {
        viewerService.getAllCoupons().then(coupons => {
            setCoupons(coupons);
        }).catch(err => console.log(err));
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

