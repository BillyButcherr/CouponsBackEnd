import { useEffect, useState } from "react";
import "./EditDialog.css";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import CouponItem from "../../../../Models/Coupon/CouponItem";

function EditDialog(props: any): JSX.Element {
    const [open, setOpen, accept, selectedCoupon] = props.hooks;
    useForm();
    function handleClose() {
        setOpen(false);
    }
    function handleAccept() {
        const coupon = new CouponItem();
        accept(coupon);
        handleClose();
    }
    return (
        <div className="EditDialog">
            <Dialog dir="rtl" open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"עריכת פרטים"}
                </DialogTitle>
                <DialogContent>
                    <br />
                    <TextField dir="rtl" label="כותרת" defaultValue={selectedCoupon?.title} /><br /><br />
                    <TextField label="Description" defaultValue={selectedCoupon?.description} /><br /><br />
                    <TextField label="Category" defaultValue={selectedCoupon?.category} /><br /><br />
                    <TextField label="Start Date" defaultValue={selectedCoupon?.startDate} /><br /><br />
                    <TextField label="End Date" defaultValue={selectedCoupon?.endDate} /><br /><br />
                    <FormControl>
                        <InputLabel htmlFor="adornment-price">Price</InputLabel>
                        <OutlinedInput
                         id="adornment-price"
                         startAdornment={<InputAdornment position="start">₪</InputAdornment>}
                         label="Price"
                         placeholder="Price"
                         defaultValue={selectedCoupon?.price}
                        />
                        
                    </FormControl>
                    <br /><br />
                    {/* <TextField label="Price" defaultValue={selectedCoupon?.price} /><br /><br /> */}
                    <TextField label="Amount" defaultValue={selectedCoupon?.amount} /><br /><br />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleAccept}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default EditDialog;
function useForm() {
    throw new Error("Function not implemented.");
}

