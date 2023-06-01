import { Tooltip } from "@mui/material";
import "./CustomerRow.css";

function CustomerRow(props: any): JSX.Element {
    const [setEditModal, setPasswordResetModal, setDeleteModal, setSelectedCustomer] = props.hooks;

    function passwordResetAction() {
        setSelectedCustomer(props.info);
        setPasswordResetModal(true);
    }
    function editAction() {
        setSelectedCustomer(props.info);
        setEditModal(true);
    }
    function deleteAction() {
        setSelectedCustomer(props.info);
        setDeleteModal(true);
    }

    return (
        <tr className="CustomerItem">
            <td>{props.info.id}</td>
            <td>{props.info.firstName}</td>
            <td>{props.info.lastName}</td>
            <td>{props.info.email}</td>
            {/* <td>{props.info.password}</td> */}
            <td>
                <Tooltip title="שינוי סיסמה">
                    <span className="material-symbols-outlined icon-hover password" onClick={passwordResetAction}>password</span>
                </Tooltip>
                <Tooltip title="עדכון פרטים">
                    <span className="material-symbols-outlined icon-hover edit" onClick={editAction}>person</span>
                </Tooltip>
                <Tooltip title="מחיקה">
                    <span className="material-symbols-outlined icon-hover delete" onClick={deleteAction}>delete</span>
                </Tooltip>
            </td>
        </tr>
    );
}

export default CustomerRow;
