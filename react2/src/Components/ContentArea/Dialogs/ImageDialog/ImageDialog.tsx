import { useEffect, useState } from "react";
import "./ImageDialog.css";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, TextField } from "@mui/material";
import CouponItem from "../../../../Models/Coupon/CouponItem";

function ImageDialog(props: any): JSX.Element {
    const [open, setOpen, accept, selectedCoupon] = props.hooks;
    const [urlValue, setUrlValue] = useState<string>("");
    function handleClose() {
        setOpen(false);
    }
    function handleAccept() {
        accept(selectedCoupon?.id, urlValue);
        handleClose();
    }
    return (
        <div className="ImageDialog">
            <Dialog open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"שינוי תמונה"}
                </DialogTitle>
                <DialogContent>
                    <br />
                    <img src={selectedCoupon?.image} width="400px"></img><br /><br />
                    <TextField label="Url" defaultValue={selectedCoupon?.image} onChange={((e) => setUrlValue(e.target.value))} /><br /><br />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default ImageDialog;
