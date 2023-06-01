import { useEffect, useState } from "react";
import CompanyRow from "./CompanyRow/CompanyRow";
import "./CompanyTable.css";
import AddCompanyDialog from "../Dialogs/AddCompanyDialog/AddCompanyDialog";
import DeleteCompanyDialog from "../Dialogs/DeleteCompanyDialog/DeleteCompanyDialog";
import EditCompanyDialog from "../Dialogs/EditCompanyDialog/EditCompanyDialog";
import ResetPasswordCompanyDialog from "../Dialogs/ResetPasswordCompanyDialog/ResetPasswordCompanyDialog";
import Company from "../../../../../Models/Company/Company";
import adminService from "../../../../../Services/AdminService";
import { companiesStore, fetchCompanies } from "../../../../../Store/AdminArea/CompaniesState";
import notificationService from "../../../../../Services/NotificationService";

function CompanyTable(props: any): JSX.Element {
    const [companyItems, setCompanyItems] = useState<Company[]>([]);
    const [selectedCompany, setSelectedCompany] = useState<FormValues>();

    const [addModal, setAddModal] = useState(false);


    const [deleteModal, setDeleteModal] = useState<boolean>(false);
    const [deletePayload, setDeletePayload] = useState<string>("");


    const [updateModal, setUpdateModal] = useState(false);

    const [companyInfo, setCompanyInfo] = useState<Company>();

    const [passwordResetModal, setPasswordResetModal] = useState(false);

    useEffect(() => {
        const unsubscribe = companiesStore.subscribe(() => {
            const updatedCompanies = companiesStore.getState().companies.slice();
            setCompanyItems(updatedCompanies);
        });
        adminService.getAllCompanies()
        .then(companies => setCompanyItems(companies))
        .catch(err => console.log(err));
        return unsubscribe;
    }, []);
    type FormValues = {
        name: string;
        email: string;
        password: string;
    }
    function addCompanyAccepted(company: Company) {
        adminService.addCompany(company)
        .then(() => notificationService.push(" החברה נוספה בהצלחה ! "))
        .catch(err => console.log(err));
    }
    function deleteFromTable(id: string) {
        adminService.deleteCompany(id)
        .then(() => notificationService.push(" החברה נמחקה בהצלחה ! "))
        .catch(err => console.log(err));
    }
    function updateCompanyAccepted(id:string, email:string) {
        adminService.getOneCompany(id).then(company => {
            company.email = email;
            console.log(company);
            adminService.updateCompany(company).then(response => {
                notificationService.push(' הדוא"ל עודכן בהצלחה ! ')
            }).catch(err => alert(err));
        }).catch(err => alert(err));
    }
    function changeCompanyPassword(companyId: string, password: string) {
        // const company = new Company(companyId, "", "", password);
        // console.log(company);
        
        adminService.getOneCompany(companyId).then(company => {
            console.log(company);
            company.password = password;
           adminService.updateCompanyPassword(company)
           .then(() => {
            notificationService.push(" הסיסמה עודכנה בהצלחה ! ");
           })
           .catch(err => console.log(err));
        });
    }
    return (
        <>
            <table className="CompanyTable">
                <thead>
                    <tr>
                        <th>מספר</th>
                        <th>שם</th>
                        <th>אימייל</th>
                        {/* <th>סיסמה</th> */}
                        <th>פעולות</th>
                    </tr>
                </thead>
                <tbody>
                    {companyItems.map((item) => (
                        <CompanyRow key={item.id} hooks={[setDeleteModal, setDeletePayload, setUpdateModal,
                            setPasswordResetModal, setCompanyInfo, setSelectedCompany]} info={item} />
                    ))}
                </tbody>
            </table>
            <button className="add-button" onClick={() => setAddModal(true)}>הוספה</button>

            <AddCompanyDialog hooks={[addModal, setAddModal, addCompanyAccepted]} />
            <EditCompanyDialog hooks={[updateModal, setUpdateModal, updateCompanyAccepted, companyInfo]} />
            <DeleteCompanyDialog hooks={[deleteModal, setDeleteModal, deleteFromTable, deletePayload]} />
            <ResetPasswordCompanyDialog hooks={[passwordResetModal, setPasswordResetModal, changeCompanyPassword, companyInfo]} />
        </>
    );
}

export default CompanyTable;
