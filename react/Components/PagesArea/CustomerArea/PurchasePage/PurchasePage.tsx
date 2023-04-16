import { useParams } from "react-router-dom";
import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import PurchaseInfo from "../../../ContentArea/PurchaseInfo/PurchaseInfo";
import "./PurchasePage.css";

function PurchasePage(): JSX.Element {
    const {id} = useParams();
    const session = {
        customerId: sessionStorage.getItem('clientId'),
        token: sessionStorage.getItem('authToken')
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
