import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import PurchasesContainer from "../../../ContentArea/CouponsArea/PurchasesContainer/PurchasesContainer";
import "./CustomerPage.css";

function CustomerPage(): JSX.Element {
    const session = {
            customerId: sessionStorage.getItem('clientId'),
            token: sessionStorage.getItem('authToken')
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
