import { useEffect, useState } from "react";
import CompanyRow from "./CompanyRow/CompanyRow";
import "./CompanyTable.css";
import AdminService from "../../../Services/AdminService";
import Company from "../../../Models/Company/Company";

function CompanyTable(props:any): JSX.Element {
    useEffect(() => {
        (new AdminService).getAllCompanies()
            .then(companies => {
                setCompanyItems(companies);
            })
            .catch(err => console.log(err));
    }, []);
    const [companyItems, setCompanyItems] = useState<Company[]>([]);
    return (
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
                    <CompanyRow key={item.id} hooks={props.hooks} tableHooks={[companyItems, setCompanyItems]} info={item}/>
                ))}
            </tbody>
        </table>
    );
}

export default CompanyTable;
