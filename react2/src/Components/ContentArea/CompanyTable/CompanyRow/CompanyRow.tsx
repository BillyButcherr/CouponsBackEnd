import { useState } from "react";
import "./CompanyRow.css";
import Company from "../../../../Models/Company/Company";
import AdminService from "../../../../Services/AdminService";
import adminService from "../../../../Services/AdminService";
import CompanyUpdateForm from "../../ModalArea/CompanyUpdateForm/CompanyUpdateForm";
import Modal from "../../ModalArea/Modal/Modal";
import { Button, ButtonGroup, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";

function CompanyRow(props: any): JSX.Element {
    const [setDeleteModal, setDeletePayload, setUpdateModal, setPasswordResetModal, setCompanyInfo] = props.hooks;

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
            <td>{props.info?.password}</td>
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

export default CompanyRow;
