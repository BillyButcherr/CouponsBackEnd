import { SyntheticEvent, useState } from "react";
import "./CompanyCouponUpdateForm.css";
import Company from "../../../../Models/Company/Company";
import adminService from "../../../../Services/AdminService";
import Modal from "../Modal/Modal";
import { Alert, Snackbar, TextField, Tooltip } from "@mui/material";

function CompanyCouponUpdateForm(props:any): JSX.Element {
    const [companyInfo, setCompanyInfo] = props.hooks;
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [openSnackbarError, setOpenSnackbarError] = useState(false);
    function submit(e: any) {
        e.preventDefault();

        const companyId = companyInfo.id;
        const companyName = e.target[0].value;
        const companyEmail = e.target[1].value;

        const updatedInfo = new Company(companyId, companyName, companyEmail, companyInfo.password);
        adminService.updateCompany(updatedInfo).then(response => {
            setOpenSnackbar(true);
        }).catch(err => {
            setOpenSnackbarError(true);
        });
        setCompanyInfo(updatedInfo);
    }
    return (
        <>
            <div className="CompanyCouponUpdateForm">
                <h3>פרטי קופון</h3>
                <form onSubmit={submit}>
                    <input type="text" id="company-name" placeholder="שם החברה" defaultValue={companyInfo?.name} />
                    <label htmlFor="email">כתובת דוא"ל</label>
                    <input type="email" id="email" placeholder="אימייל" defaultValue={companyInfo?.email} />
                    <input type="submit" value="עדכון פרטים" />
                </form>
            </div>
            <Snackbar open={openSnackbar} anchorOrigin={{ vertical: "top", horizontal: "center" }} onClose={() => setOpenSnackbar(false)}>
                <Alert severity="success">הפרטים נשמרו בהצלחה</Alert>
            </Snackbar>
            <Snackbar open={openSnackbarError} anchorOrigin={{ vertical: "top", horizontal: "center" }} onClose={() => setOpenSnackbarError(false)}>
                <Alert severity="error">שגיאה, לא ניתן לשמור את השינויים</Alert>
            </Snackbar>
        </>

    );
}

export default CompanyCouponUpdateForm;
