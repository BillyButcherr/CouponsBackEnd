import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import { useForm } from "react-hook-form";

function EditCompanyDialog(props: any): JSX.Element {
    const [open, setOpen, accept, companyInfo] = props.hooks;
    const [email, setEmail] = useState<string>("");
    useEffect(() => {
        loadDefaultValues();
    }, []);
    
    function handleClose() {
        setOpen(false);
    }
    function loadDefaultValues(){
        setEmail(companyInfo?.email);
    }
    function handleValidation(){
        
    }
    function handleAccept() {
        const company = {...companyInfo};
        company.email = email;
        if(!email)
            company.email = companyInfo?.email;
        accept(company.id, company.email);
        handleClose();
    }
    return (
        <div className="EditCompanyDialog">
            <Dialog dir="rtl" open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {'שינוי כתובת דוא"ל'}
                </DialogTitle>
                <DialogContent>
                    {/* <h1>{companyInfo?.email}</h1> */}
                    <br />
                    {/* <TextField label="Company Name" 
                    defaultValue={companyInfo?.name}
                    onChange={(e) => setName(e.target.value)} 
                    // helperText={errors.name?.message}
                     />
                    <br /><br /> */}
                    <TextField label="Email"
                    defaultValue={companyInfo?.email}
                    onChange={(e) => setEmail(e.target.value)}
                    // helperText={errors.email?.message} 
                    />
                    <br /><br />
                    {/* <TextField label="Company Name" {...register("name", {
                        required: "company name is required."
                        // validate: (fieldValue) => {
                        //     return true;
                        // }
                    })} helperText={errors.name?.message} />
                    <br /><br />
                    <TextField label="Email" {...register("email", {
                        required: "email is required."
                    })} helperText={errors.email?.message} />
                    <br /><br /> */}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default EditCompanyDialog;

