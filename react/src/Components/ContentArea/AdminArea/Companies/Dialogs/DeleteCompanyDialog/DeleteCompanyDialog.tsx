import { useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material";

function DeleteCompanyDialog(props:any): JSX.Element {
    const [open, setOpen, accept, currentDelete] = props.hooks;
    function handleClose(){
        setOpen(false);
    }
    function handleAccept(){
        accept(currentDelete);
        handleClose();
    }
    return (
        <div className="DeleteCompanyDialog">
			<Dialog open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"למחוק פריט זה?"}
                </DialogTitle>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default DeleteCompanyDialog;
