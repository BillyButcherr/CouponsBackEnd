import { useEffect, useState } from "react";
import Coupon from "../Coupon/Coupon";
import "./PurchasesContainer.css";
import CouponItem from "../../../../Models/Coupon/CouponItem";
import { PurchasesStore } from "../../../../Store/PurchasesState";
import CustomerService from "../../../../Services/CustomerService";
import { fetchPurchases } from "../../../../Store/PurchasesState";
import { useNavigate } from "react-router-dom";
import PurchaseCard from "../PurchaseCard/PurchaseCard";

function PurchasesContainer(props:any): JSX.Element {
    console.log(props);
    console.log(props.session.customerId);
    console.log(props.session.token);
    
    const [Purchases, setPurchases] = useState<CouponItem[]>([]);

    useEffect(() => {
        if (PurchasesStore.getState().coupons.length > 0) {
            setPurchases(PurchasesStore.getState().coupons);
        } else {
            (new CustomerService(props.session.customerId, props.session.token)).getAllPurchasedCoupons().then(purchases => {
                PurchasesStore.dispatch(fetchPurchases(purchases));
                setPurchases(purchases);
            }).catch(err => console.log(err));
        }

    }, []);
    
    return (
        <div className="purchases-container">
            {Purchases.map((coupon) => (
                <PurchaseCard key={coupon.id} info={coupon}/>
            ))}
        </div>
    );
}

export default PurchasesContainer;

