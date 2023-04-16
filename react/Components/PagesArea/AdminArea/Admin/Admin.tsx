import { useState } from "react";
import CompanyTable from "../../../ContentArea/CompanyTable/CompanyTable";
import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import CustomerTable from "../../../ContentArea/CustomerTable/CustomerTable";
import Modal from "../../ModalArea/Modal/Modal";
import "./Admin.css";
import Company from "../../../../Models/Company/Company";
import CompanyUpdateForm from "../../../ContentArea/CompanyUpdateForm/CompanyUpdateForm";
import CustomerUpdateForm from "../../../ContentArea/CustomerUpdateForm/CustomerUpdateForm";
import Customer from "../../../../Models/Customer/Customer";

function Admin(): JSX.Element {
    const [companyModal, setCompanyModal] = useState(false);
    const [customerModal, setCustomerModal] = useState(false);

    const [companyInfo, setCompanyInfo] = useState<Company>();
    const [customerInfo, setCustomerInfo] = useState<Customer>();


    return (
        <div className="Admin">
			 <ContentContainer>
                <Content title="רשימת חברות">
                    <CompanyTable hooks={[companyModal, setCompanyModal, companyInfo, setCompanyInfo]}/>
                </Content>
                <Content title="רשימת לקוחות">
                    <CustomerTable hooks={[customerModal, setCustomerModal, customerInfo, setCustomerInfo]}/>
                </Content>
            </ContentContainer>
            <Modal firstHook={companyModal} secondHook={setCompanyModal}>
                <CompanyUpdateForm hooks={[companyModal, setCompanyModal, companyInfo, setCompanyInfo]}/>
            </Modal>
            <Modal firstHook={customerModal} secondHook={setCustomerModal}>
                <CustomerUpdateForm hooks={[customerModal, setCustomerModal, customerInfo, setCustomerInfo]}/>
            </Modal>
        </div>
    );
}

export default Admin;
