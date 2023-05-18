import CompanyTable from "../../../ContentArea/CompanyTable/CompanyTable";
import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import CustomerTable from "../../../ContentArea/CustomerTable/CustomerTable";
import "./Admin.css";

function Admin(): JSX.Element {
    return (
        <div className="Admin">
            <ContentContainer>
                <Content title="רשימת חברות">
                    <CompanyTable/>
                </Content>
                <Content title="רשימת לקוחות">
                    <CustomerTable/>
                </Content>
            </ContentContainer>
        </div>
    );
}

export default Admin;
