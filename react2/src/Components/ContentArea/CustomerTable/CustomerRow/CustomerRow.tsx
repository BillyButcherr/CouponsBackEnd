import { Tooltip } from "@mui/material";
import "./CustomerRow.css";

function CustomerRow(props: any): JSX.Element {
    const [setDeleteModal, setDeletePayload, setUpdateModal, setPasswordResetModal, setCustomerInfo] = props.hooks;

    function passwordResetAction() {
        setCustomerInfo(props.info);
        setPasswordResetModal(true);
    }
    function editAction() {
        setCustomerInfo(props.info);
        setUpdateModal(true);
    }
    function deleteAction() {
        setDeletePayload(props.info.id);
        setDeleteModal(true);
    }

    return (
        <tr className="CustomerItem">
            <td>{props.info.id}</td>
            <td>{props.info.firstName}</td>
            <td>{props.info.lastName}</td>
            <td>{props.info.email}</td>
            <td>{props.info.password}</td>
            <td>
                <Tooltip title="שינוי סיסמה">
                    <span className="material-symbols-outlined icon-hover password" onClick={passwordResetAction}>password</span>
                </Tooltip>
                <Tooltip title="עריכה">
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
