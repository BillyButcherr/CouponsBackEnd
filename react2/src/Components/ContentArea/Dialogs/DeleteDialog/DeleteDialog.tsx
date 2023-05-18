import { useState } from "react";
import "./DeleteDialog.css";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material";

function DeleteDialog(props:any): JSX.Element {
    const [open, setOpen, accept, currentDelete] = props.hooks;
    // const [open, setOpen] = useState(false);
    function handleClickOpen(){
        setOpen(true);
    }
    function handleClose(){
        setOpen(false);
    }
    function handleAccept(){
        accept(currentDelete);
        handleClose();
    }
    return (
        <div className="DeleteDialog">
			<Dialog open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"למחוק פריט זה?"}
                </DialogTitle>
                {/* <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        {"למחוק פריט זה?"}
                    </DialogContentText>
                </DialogContent> */}
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default DeleteDialog;
