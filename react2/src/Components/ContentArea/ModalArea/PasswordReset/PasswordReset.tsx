import { SyntheticEvent, useState } from "react";
import "./PasswordReset.css";
import Company from "../../../../Models/Company/Company";
import adminService from "../../../../Services/AdminService";
import Modal from "../Modal/Modal";
import { Alert, Snackbar, TextField, Tooltip } from "@mui/material";

function PasswordReset(props: any): JSX.Element {
    const [modal, setModal, companyPassword, setCompanyPassword, companyInfo] = props.hooks;
    const [open, setOpen] = useState(false);
    const [severity, setSeverity] = useState("success");
    function submit(e: any) {
        e.preventDefault();

        const companyPassword = e.target[0].value;
        const companyPasswordAgain = e.target[1].value;

        const updatedInfo = new Company(companyInfo.id, companyInfo.name, companyInfo.email, companyPassword);
        adminService.updateCompany(updatedInfo).then(response => {
            setSeverity("success");
            setOpen(true);
            // alert(response);
        }).catch(err => {
            setSeverity("error");
            setOpen(true);
        });
        // setCompanyInfo(updatedInfo);
    }
    return (
        <Modal firstHook={modal} secondHook={setModal}>
            <div className="CustomerUpdateForm">
                <h3>שינוי סיסמה</h3>
                <form onSubmit={submit}>
                    <input type="password" id="password" placeholder="סיסמה חדשה" />
                    {/* <label htmlFor="password-again">הקלד סיסמה שנית</label> */}
                    <input type="password" id="password-again" placeholder="הקלד שנית" />
                    <input type="submit" value="שמירה" />
                </form>
            </div>
            <Snackbar open={open} anchorOrigin={{ vertical: "top", horizontal: "center" }} onClose={() => setOpen(false)}>
                <Alert severity="success">סיסמה עודכנה בהצלחה</Alert>
            </Snackbar>
        </Modal>

    );
}

export default PasswordReset;
