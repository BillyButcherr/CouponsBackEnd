import "./PurchaseInfo.css";
import { useEffect, useState } from "react";
import CustomerService from "../../../Services/CustomerService";
import CouponItem from "../../../Models/Coupon/CouponItem";
import PurchasesTable from "../PurchasesTable/PurchasesTable";

function PurchaseInfo(props: any): JSX.Element {
    const purchaseId = props.id;
    const [purchase, setPurchase] = useState<CouponItem>();
    useEffect(() => {
        (new CustomerService(props.session.customerId, props.session.token)).getOnePurchasedCoupon(purchaseId).then(purchase => {
            setPurchase(purchase);
        }).catch(err => console.log(err));
    }, []);
    

    return (
        <div className="purchase-container">
            <PurchasesTable purchase={purchase} />
            {/* <PurchasesTable purchase={purchase} /> */}
            {/* <article className="PurchaseInfo">
                <figure className="article-image">
                    <img src={purchase?.image} />
                </figure>
                <div className="article-content">
                    <h5>{purchase?.title}</h5>
                </div>
            </article> */}
        </div>
    );
}

export default PurchaseInfo;
