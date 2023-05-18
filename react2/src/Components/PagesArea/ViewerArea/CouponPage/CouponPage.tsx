import { useParams } from "react-router-dom";
import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import "./CouponPage.css";
import CouponEnlarge from "../../../ContentArea/CouponsArea/CouponEnlarge/CouponEnlarge";
import { useEffect, useState } from "react";
import CouponItem from "../../../../Models/Coupon/CouponItem";
import viewerService from "../../../../Services/ViewerService";

function CouponPage(props: any): JSX.Element {
    const { id } = useParams();
    const session = {
        customerId: sessionStorage.getItem('clientId'),
        token: sessionStorage.getItem('authToken')
    };
    const [couponView, setCouponView] = useState<CouponItem>();

    useEffect(() => {
        if (id != undefined) {
            viewerService.getOneCoupon(id).then(coupon => {
                setCouponView(coupon);
            }).catch(err => console.log(err));
        } else{
            alert(id);
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
