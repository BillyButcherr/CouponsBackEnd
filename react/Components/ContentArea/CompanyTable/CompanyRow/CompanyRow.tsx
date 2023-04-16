import { useState } from "react";
import "./CompanyRow.css";
import Company from "../../../../Models/Company/Company";

function CompanyRow(props: any): JSX.Element {
    const [modal, setModal, companyInfo, setCompanyInfo] = props.hooks;
    const [companyItems, setCompanyItems] = props.tableHooks;

    function editAction(){
        setCompanyInfo(props.info);
        setModal(true);
        // alert(props.id);
    }
    function deleteAction(){
        const updatedCompanyItems = companyItems.filter((item:Company) => {
            return item.id !== props.info.id;
        });
        setCompanyItems(updatedCompanyItems);
    }
    return (
        <tr className="CompanyItem">
            <td>{props.info.id}</td>
            <td>{props.info.name}</td>
            <td>{props.info.email}</td>
            <td>{props.info.password}</td>
            <td>
                <span className="material-symbols-outlined icon-hover edit" onClick={editAction}>edit</span>
                <span className="material-symbols-outlined icon-hover delete" onClick={deleteAction}>delete</span>
            </td>
        </tr>
    );
}

export default CompanyRow;
