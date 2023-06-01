import CompanyTable from "../../../ContentArea/AdminArea/Companies/CompanyTable/CompanyTable";
import CustomerTable from "../../../ContentArea/AdminArea/Customers/CustomerTable/CustomerTable";
import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
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
