import { useEffect, useState } from "react";
import Coupon from "../Coupon/Coupon";
import "./PurchasesContainer.css";
import CouponItem from "../../../../Models/Coupon/CouponItem";
import CustomerService from "../../../../Services/CustomerService";
import PurchaseCard from "../PurchaseCard/PurchaseCard";

function PurchasesContainer(props: any): JSX.Element {
    console.log(props);
    console.log(props.session.customerId);
    console.log(props.session.token);

    const [Purchases, setPurchases] = useState<CouponItem[]>([]);

    useEffect(() => {
        (new CustomerService(props.session.customerId, props.session.token)).getAllPurchasedCoupons().then(purchases => {
            setPurchases(purchases);
        }).catch(err => console.log(err));
    }, []);

    return (
        <div className="purchases-container">
            {Purchases.map((purchase) => (
                <PurchaseCard key={purchase.id} info={purchase} />
            ))}
        </div>
    );
}

export default PurchasesContainer;

