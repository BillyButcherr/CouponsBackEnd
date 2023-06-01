import { useNavigate, useParams } from "react-router-dom";
import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import "./CouponPage.css";
import { useEffect, useState } from "react";
import CouponItem from "../../../../Models/Coupon/CouponItem";
import viewerService from "../../../../Services/ViewerService";
import CustomerService from "../../../../Services/CustomerService";
import CouponEnlarge from "../../../ContentArea/HomeArea/CouponEnlarge/CouponEnlarge";
import authService from "../../../../Services/AuthService";

function CouponPage(props: any): JSX.Element {
    const navigate = useNavigate();
    const { id } = useParams();
    
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
    function handlePurchase(){
        const session = {
            customerId: authService.getAuthData().clientId,
            token: authService.getAuthData().token
        };
        if(session.customerId && session.token && couponView){
            (new CustomerService(session.customerId, session.token))
            .purchaseCoupon(couponView).then(response => {
                alert("נרכש בהצלחה");
                //purchased
            }).catch(err => alert("לא ניתן לרכוש קופון"));   
        }
        else {
            navigate("../login");
        }
    }
    return (
        <div className="CouponPage">
            <ContentContainer>
                <Content title="פרטי קופון">
                    <CouponEnlarge info={couponView} />
                    <button className="add-button" onClick={handlePurchase}>רכישה</button>
                </Content>
            </ContentContainer>
        </div>
    );
}

export default CouponPage;
