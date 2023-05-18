import { useState } from "react";
import CouponsTable from "../../../ContentArea/CompanyArea/Tables/CouponsTable/CouponsTable";
import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import "./CompanyPage.css";

function CompanyPage(): JSX.Element {
    return (
        <div className="CompanyPage">
			 <ContentContainer>
                <Content title=" רשימת קופונים">
                    <CouponsTable/>
                </Content>
            </ContentContainer>
        </div>
    );
}

export default CompanyPage;
