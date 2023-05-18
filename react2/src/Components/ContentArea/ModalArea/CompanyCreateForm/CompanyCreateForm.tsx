import { SyntheticEvent, useState } from "react";
import "./CompanyCreateForm.css";
import Company from "../../../../Models/Company/Company";
import adminService from "../../../../Services/AdminService";
import Modal from "../Modal/Modal";
import { Alert, Snackbar, TextField, Tooltip } from "@mui/material";

function CompanyCreateForm(props: any): JSX.Element {
    const [modal, setModal, companyInfo, setCompanyInfo] = props.hooks;
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [openSnackbarError, setOpenSnackbarError] = useState(false);

    function submit(e: any) {
        e.preventDefault();

        const companyId = companyInfo.id;
        const companyName = e.target[0].value;
        const companyEmail = e.target[1].value;
        const companyPassword = e.target[2].value;
        const companyPasswordAgain = e.target[3].value;

        const info = new Company(companyId, companyName, companyEmail, companyInfo.password);
        adminService.addCompany(info).then(response => {
            setOpenSnackbar(true);
            // alert(response);
        }).catch(err => {
            setOpenSnackbarError(true);
        });
        setCompanyInfo(info);
    }
    return (
        <Modal firstHook={modal} secondHook={setModal}>
            <div className="CustomerUpdateForm">
                <h3>פרטי חברה</h3>
                <form onSubmit={submit}>
                    <Tooltip title="hello">
                        <label htmlFor="company-name">שם החברה</label>
                    </Tooltip>
                    <input type="text" id="company-name" placeholder="שם החברה" defaultValue={companyInfo?.name} />
                    <label htmlFor="email">כתובת דוא"ל</label>
                    <input type="email" id="email" placeholder="אימייל" defaultValue={companyInfo?.email} />
                    <label htmlFor="password">סיסמה</label>
                    <input type="password" id="password" placeholder="סיסמה" />
                    <label htmlFor="password-again">הקלד סיסמה שנית</label>
                    <input type="password" id="password-again" placeholder="הקלד שנית" />
                    <input type="submit" value="עדכון פרטים" />
                </form>
            </div>
            <Snackbar open={openSnackbar} anchorOrigin={{ vertical: "top", horizontal: "center" }} onClose={() => setOpenSnackbar(false)}>
                <Alert severity="success">החברה נוספה בהצלחה</Alert>
            </Snackbar>
            <Snackbar open={openSnackbarError} anchorOrigin={{ vertical: "top", horizontal: "center" }} onClose={() => setOpenSnackbarError(false)}>
                <Alert severity="error">שגיאה, לא ניתן להוסיף חברה</Alert>
            </Snackbar>
        </Modal>

    );
}

export default CompanyCreateForm;
