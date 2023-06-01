import { useEffect, useState } from "react";
import "./CustomerTable.css";
import CustomerRow from "./CustomerRow/CustomerRow";
import Customer from "../../../../../Models/Customer/Customer";
import adminService from "../../../../../Services/AdminService";
import AddCustomerDialog from "../Dialogs/AddCustomerDialog";
import DeleteCustomerDialog from "../Dialogs/DeleteCustomerDialog";
import EditCustomerDialog from "../Dialogs/EditCustomerDialog";
import ResetPasswordCustomerDialog from "../Dialogs/ResetPasswordCustomerDialog";
import { customersStore, fetchCustomers } from "../../../../../Store/AdminArea/CustomersState";
import notificationService from "../../../../../Services/NotificationService";

function CustomerTable(props: any): JSX.Element {
    const [customerItems, setCustomerItems] = useState<Customer[]>([]);

    const [addModal, setAddModal] = useState<boolean>(false);
    const [editModal, setEditModal] = useState<boolean>(false);
    const [passwordResetModal, setPasswordResetModal] = useState<boolean>(false);
    const [deleteModal, setDeleteModal] = useState<boolean>(false);

    const [selectedCustomer, setSelectedCustomer] = useState<Customer>();

    useEffect(() => {
        const unsubscribe = customersStore.subscribe(() => {
            setCustomerItems([...customersStore.getState().customers]);
        });
        adminService.getAllCustomers()
            .then(customers => customersStore.dispatch(fetchCustomers(customers)))
            .catch(err => console.log(err));
        return unsubscribe;
    }, []);

    function showAddCustomerModal() {
        setAddModal(true);
    }
    function addCustomerAccepted(customer: Customer) {
        adminService.addCustomer(customer)
            .then(() => notificationService.push(" הלקוח נוסף בהצלחה !"))
            .catch(err => console.log(err));
    }
    function editCustomerAccepted(customerId: string, firstName: string, lastName: string, email: string) {
        adminService.getOneCustomer(customerId)
            .then(customer => {
                customer.firstName = firstName;
                customer.lastName = lastName;
                customer.email = email;
                adminService.updateCustomer(customer).then(response => {
                    notificationService.push(" הלקוח עודכן בהצלחה !");
                })
                    .catch(err => alert(err));
            }).catch(err => alert(err));
    }
    function changePasswordAccepted(customerId: string, password: string) {
        adminService.getOneCustomer(customerId)
            .then(customer => {
                customer.password = password;
                adminService.updateCustomerPassword(customer)
                    .then((response) => notificationService.push(" הסיסמה עודכנה בהצלחה !"))
                    .catch(err => console.log(err));
            });
    }
    function deleteCustomerAccepted(customerId: string) {
        adminService.deleteCustomer(customerId)
        .then(response => notificationService.push(" הלקוח נמחק בהצלחה !"))
        .catch(err => alert(err));
    }
    return (
        <>
            <table className="CustomerTable">
                <thead>
                    <tr>
                        <th>מספר</th>
                        <th>שם פרטי</th>
                        <th>שם משפחה</th>
                        <th>אימייל</th>
                        <th>פעולות</th>
                    </tr>
                </thead>
                <tbody>
                    {customerItems.map((item) => (
                        <CustomerRow key={item.id} hooks={[setEditModal, setPasswordResetModal, setDeleteModal, setSelectedCustomer]} info={item} />
                    ))}
                </tbody>
            </table>
            <button className="add-button" onClick={showAddCustomerModal}>הוספה</button>

            <AddCustomerDialog hooks={[addModal, setAddModal, addCustomerAccepted]} />
            <EditCustomerDialog hooks={[editModal, setEditModal, editCustomerAccepted, selectedCustomer]} />
            <DeleteCustomerDialog hooks={[deleteModal, setDeleteModal, deleteCustomerAccepted, selectedCustomer]} />
            <ResetPasswordCustomerDialog hooks={[passwordResetModal, setPasswordResetModal, changePasswordAccepted, selectedCustomer]} />

        </>
    );
}

export default CustomerTable;
