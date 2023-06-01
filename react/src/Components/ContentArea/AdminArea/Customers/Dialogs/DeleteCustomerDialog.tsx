import { useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material";

function DeleteCustomerDialog(props:any): JSX.Element {
    const [open, setOpen, accept, selectedCustomer] = props.hooks;
    function handleClose(){
        setOpen(false);
    }
    function handleAccept(){
        accept(selectedCustomer.id);
        handleClose();
    }
    return (
        <div className="DeleteCustomerDialog">
			<Dialog open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"למחוק לקוח זה?"}
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default DeleteCustomerDialog;
