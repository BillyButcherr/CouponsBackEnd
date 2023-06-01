import { useParams } from "react-router-dom";
import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import PurchaseInfo from "../../../ContentArea/CustomerArea/PurchasePage/PurchaseInfo/PurchaseInfo";
import "./PurchasePage.css";
import authService from "../../../../Services/AuthService";

function PurchasePage(): JSX.Element {
    const {id} = useParams();
    const session = {
        customerId: authService.getAuthData().clientId,
        token: authService.getAuthData().token
    };
    return (
        <div className="PurchasePage">
			<ContentContainer>
                <Content title="פרטי רכישה">
                    <PurchaseInfo id={id} session={session}/>
                </Content>
            </ContentContainer>
        </div>
    );
}

export default PurchasePage;
