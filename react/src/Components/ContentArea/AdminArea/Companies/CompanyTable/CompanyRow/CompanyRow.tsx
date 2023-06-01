import "./CompanyRow.css";
import { Button, ButtonGroup, Tooltip } from "@mui/material";

function CompanyRow(props: any): JSX.Element {
    const [setDeleteModal, setDeletePayload, setUpdateModal, setPasswordResetModal, setCompanyInfo, setSelectedCompany] = props.hooks;

    function passwordResetAction() {
        setCompanyInfo(props.info);
        setPasswordResetModal(true);
    }
    function editAction() {
        setCompanyInfo(props.info);
        setUpdateModal(true);
    }
    function deleteAction() {
        setDeletePayload(props.info.id);
        setDeleteModal(true);
    }
    return (
        <tr className="CompanyItem">
            <td>{props.info?.id}</td>
            <td>{props.info?.name}</td>
            <td>{props.info?.email}</td>
            {/* <td>{props.info?.password}</td> */}
            <td>
                <Tooltip title="שינוי סיסמה">
                    <span className="material-symbols-outlined icon-hover password" onClick={passwordResetAction}>password</span>
                </Tooltip>
                <Tooltip title="שינוי אימייל">
                    <span className="material-symbols-outlined icon-hover edit" onClick={editAction}>person</span>
                </Tooltip>
                <Tooltip title="מחיקה">
                    <span className="material-symbols-outlined icon-hover delete" onClick={deleteAction}>delete</span>
                </Tooltip>
            </td>
        </tr>
    );
}

export default CompanyRow;
