import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import PurchasesContainer from "../../../ContentArea/CustomerArea/PurchasesContainer/PurchasesContainer";
import "./CustomerPage.css";
import authService from "../../../../Services/AuthService";

function CustomerPage(): JSX.Element {
    const session = {
        customerId: authService.getAuthData().clientId,
        token: authService.getAuthData().token
        };
    
    return (
        <div className="CustomerPage">
            <ContentContainer>
                <Content title="רשימת רכישות">
                    <PurchasesContainer session={session} />
                </Content>
            </ContentContainer>
        </div>
    );
}

export default CustomerPage;
