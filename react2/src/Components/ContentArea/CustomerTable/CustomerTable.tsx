import { useEffect, useState } from "react";
import "./CustomerTable.css";
import Customer from "../../../Models/Customer/Customer";
import CustomerRow from "./CustomerRow/CustomerRow";
import adminService from "../../../Services/AdminService";
import DeleteConfirmation from "../ModalArea/DeleteConfirmation/DeleteConfirmation";
import CustomerUpdateForm from "../ModalArea/CustomerUpdateForm/CustomerUpdateForm";
import PasswordReset from "../ModalArea/PasswordReset/PasswordReset";

function CustomerTable(props: any): JSX.Element {
    const [customerItems, setCustomerItems] = useState<Customer[]>([]);

    const [deleteModal, setDeleteModal] = useState<boolean>(false);
    const [deletePayload, setDeletePayload] = useState<string>("");


    const [updateModal, setUpdateModal] = useState(false);
    const [customerInfo, setCustomerInfo] = useState<Customer>();

    const [passwordResetModal, setPasswordResetModal] = useState(false);
    const [customerPassword, setCustomerPassword] = useState<string>();


    function deleteFromTable(id: string) {
        adminService.deleteCustomer(id);
        const updatedCustomerItems = customerItems.filter((item: Customer) => {
            return item.id !== id;
        });
        setCustomerItems(updatedCustomerItems);
    }
    function updateTable(customer: Customer) {
        adminService.updateCustomer(customer).then(response => {
            alert(response);
        }).catch(err => alert(err));
        
        // let updatedCustomerItems:Customer[] = {...customerItems};
        // // for (let i = 0; i < customerItems.length; i++) {
        // //     const item = updatedCustomerItems[i];
        // //     if(item.id === customer.id){
        // //         updatedCustomerItems[i] = customer;
        // //     }
        // // }
        // Array.from(updatedCustomerItems);
        // for (let i = 0; i < updatedCustomerItems.length; i++) {
        //     const item = updatedCustomerItems[i];
        //     if(item.id === customer.id){
        //         updatedCustomerItems[i] = customer;
        //     }
        // }
        // setCustomerItems(updatedCustomerItems);
        // console.log(updatedCustomerItems);
    }
    useEffect(() => {
        adminService.getAllCustomers()
            .then(customers => setCustomerItems(customers))
            .catch(err => console.log(err));
    }, []);
    return (
        <>
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
                    {customerItems.map((item) => (
                        <CustomerRow key={item.id} hooks={[setDeleteModal, setDeletePayload, setUpdateModal, setCustomerInfo]} info={item} />
                    ))}
                </tbody>
            </table>
            <DeleteConfirmation hooks={[deleteModal, setDeleteModal, deletePayload, setDeletePayload, deleteFromTable]}/>
            <CustomerUpdateForm hooks={[updateModal, setUpdateModal, customerInfo, setCustomerInfo, updateTable]} />
            <PasswordReset  hooks={[passwordResetModal, setPasswordResetModal, customerPassword, setCustomerPassword, customerInfo]}/>

        </>
    );
}

export default CustomerTable;
