import { useEffect, useState } from "react";
import CompanyRow from "./CompanyRow/CompanyRow";
import "./CompanyTable.css";
import Company from "../../../Models/Company/Company";
import adminService from "../../../Services/AdminService";
import CompanyUpdateForm from "../ModalArea/CompanyUpdateForm/CompanyUpdateForm";
import DeleteConfirmation from "../ModalArea/DeleteConfirmation/DeleteConfirmation";
import PasswordReset from "../ModalArea/PasswordReset/PasswordReset";
import CompanyCreateForm from "../ModalArea/CompanyCreateForm/CompanyCreateForm";

function CompanyTable(props: any): JSX.Element {
    const [companyItems, setCompanyItems] = useState<Company[]>([]);

    const [deleteModal, setDeleteModal] = useState<boolean>(false);
    const [deletePayload, setDeletePayload] = useState<string>("");


    const [updateModal, setUpdateModal] = useState(false);
    const [createModal, setCreateModal] = useState(false);

    const [companyInfo, setCompanyInfo] = useState<Company>();

    const [passwordResetModal, setPasswordResetModal] = useState(false);
    const [companyPassword, setCompanyPassword] = useState<string>();

    useEffect(() => {
        adminService.getAllCompanies()
            .then(companies => {
                setCompanyItems(companies);
            })
            .catch(err => console.log(err));
    }, []);
    function addCompany(){
        setCreateModal(true);
    }
    function deleteFromTable(id: string) {
        adminService.deleteCompany(id);
        const updatedCompanyItems = companyItems.filter((item: Company) => item.id !== id);
        setCompanyItems(updatedCompanyItems);
    }
    function updateTable(company: Company) {
        adminService.updateCompany(company).then(response => {
            alert(response);
        }).catch(err => alert(err));
    }
    return (
        <>
            <table className="CompanyTable">
                <thead>
                    <tr>
                        <th>מספר</th>
                        <th>שם</th>
                        <th>אימייל</th>
                        <th>סיסמה</th>
                        <th>פעולות</th>
                    </tr>
                </thead>
                <tbody>
                    {companyItems.map((item) => (
                        <CompanyRow key={item.id} hooks={[setDeleteModal, setDeletePayload, setUpdateModal,
                             setPasswordResetModal, setCompanyInfo]} info={item} />
                    ))}
                </tbody>
            </table>
            <button className="add-button" onClick={addCompany}>הוספה</button>
            <CompanyCreateForm  hooks={[createModal, setCreateModal, companyInfo, setCompanyInfo, updateTable]}/>
            <DeleteConfirmation hooks={[deleteModal, setDeleteModal, deletePayload, setDeletePayload, deleteFromTable]}/>
            <CompanyUpdateForm  hooks={[updateModal, setUpdateModal, companyInfo, setCompanyInfo, updateTable]}/>
            <PasswordReset  hooks={[passwordResetModal, setPasswordResetModal, companyPassword, setCompanyPassword, companyInfo]}/>
        </>
    );
}

export default CompanyTable;
