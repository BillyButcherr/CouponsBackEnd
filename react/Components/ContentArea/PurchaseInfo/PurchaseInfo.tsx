import { useParams } from "react-router-dom";
import "./PurchaseInfo.css";
import { useEffect, useState } from "react";
import CustomerService from "../../../Services/CustomerService";
import { PurchasesStore, fetchPurchases } from "../../../Store/PurchasesState";
import CouponItem from "../../../Models/Coupon/CouponItem";
import PurchasesRow from "../PurchasesTable/PurchasesRow/PurchasesRow";
import PurchasesTable from "../PurchasesTable/PurchasesTable";

function PurchaseInfo(props: any): JSX.Element {
    const purchaseId = props.id;
    const [purchase, setPurchase] = useState<CouponItem>();
    useEffect(() => {
        if (PurchasesStore.getState().coupons.length > 0) {
            const index = PurchasesStore.getState().coupons.findIndex(coupon => coupon.id == purchaseId);
            setPurchase(PurchasesStore.getState().coupons[index]);
            // console.log(purchase);
        } else {
            (new CustomerService(props.session.customerId, props.session.token)).getAllPurchasedCoupons().then(purchases => {
                PurchasesStore.dispatch(fetchPurchases(purchases));
                const index = PurchasesStore.getState().coupons.findIndex(coupon => coupon.id == purchaseId);
                setPurchase(PurchasesStore.getState().coupons[index]);
            }).catch(err => console.log(err));
        }

    }, []);

    return (
        <div className="purchase-container">
            <PurchasesTable purchase={purchase} />
            <article className="PurchaseInfo">
                <figure className="article-image">
                    <img src={purchase?.image} />
                </figure>
                <div className="article-content">
                    <h5>{purchase?.title}</h5>
                </div>
            </article>
        </div>
    );
}

export default PurchaseInfo;
