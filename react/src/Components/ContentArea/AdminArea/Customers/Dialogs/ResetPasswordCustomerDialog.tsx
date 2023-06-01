import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import { useForm } from "react-hook-form";

function ResetPasswordCustomerDialog(props: any): JSX.Element {
    const [open, setOpen, accept, selectedCustomer] = props.hooks;
    const [password, setPassword] = useState<string>("");
   

    function handleClose() {
        setOpen(false);
    }
    function handleAccept() {
        accept(selectedCustomer?.id, password);
        handleClose();
    }
    return (
        <div className="ResetPasswordCustomerDialog">
            <Dialog dir="rtl" open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"שינוי סיסמה"}
                </DialogTitle>
                <DialogContent>
                    <br />
                    <TextField label="New Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    />
                    <br /><br />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default ResetPasswordCustomerDialog;

