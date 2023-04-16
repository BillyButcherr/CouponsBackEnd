import { useEffect, useState } from "react";
import AdminService from "../../../Services/AdminService";
import "./CustomerTable.css";
import Customer from "../../../Models/Customer/Customer";
import CustomerRow from "./CustomerRow/CustomerRow";

function CustomerTable(props:any): JSX.Element {
    const [customerItems, setCustomerItems] = useState<Customer[]>([]);
    useEffect(() => {
        (new AdminService).getAllCustomers()
            .then(customers => setCustomerItems(customers))
            .catch(err => console.log(err));
    }, []);
    return (
        <table className="CustomerTable">
            <thead>
                <tr>
                    <th>מספר</th>
                    <th>שם פרטי</th>
                    <th>שם משפחה</th>
                    <th>אימייל</th>
                    <th>סיסמה</th>
                    <th>פעולות</th>
                </tr>
            </thead>
            <tbody>
                {customerItems.map(item => (
                    <CustomerRow key={item.id} hooks={props.hooks} tableHooks={[customerItems, setCustomerItems]} info={item}/>
                ))}
            </tbody>
        </table>
    );
}

export default CustomerTable;
