import { useParams } from "react-router-dom";
import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import "./CouponPage.css";
import CouponEnlarge from "../../../ContentArea/CouponsArea/CouponEnlarge/CouponEnlarge";
import { useEffect, useState } from "react";
import { CouponsStore, fetchCoupons } from "../../../../Store/CouponsState";
import CouponService from "../../../../Services/ViewerService";
import CouponItem from "../../../../Models/Coupon/CouponItem";

function CouponPage(props: any): JSX.Element {
    const { couponInViewId } = useParams();
    const session = {
        customerId: sessionStorage.getItem('clientId'),
        token: sessionStorage.getItem('authToken')
    };
    const [couponView, setCouponView] = useState<CouponItem>();

    useEffect(() => {
        if (CouponsStore.getState().coupons.length > 0) {
            const index = CouponsStore.getState().coupons.findIndex(coupon => coupon.id === couponInViewId);
            setCouponView(CouponsStore.getState().coupons[index]);
        } else {
            CouponService.getAllCoupons().then(coupons => {
                CouponsStore.dispatch(fetchCoupons(coupons));
                const index = CouponsStore.getState().coupons.findIndex(coupon => coupon.id === couponInViewId);
                setCouponView(CouponsStore.getState().coupons[index]);
            }).catch(err => console.log(err));
        }
    }, []);

    return (
        <div className="CouponPage">
            <ContentContainer>
                <Content title="פרטי קופון">
                    <CouponEnlarge info={couponView} />
                </Content>
            </ContentContainer>
        </div>
    );
}

export default CouponPage;
