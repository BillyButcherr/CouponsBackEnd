import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import CouponItem from "../../../../Models/Coupon/CouponItem";

function AddDialog(props: any): JSX.Element {
    const [open, setOpen, accept, selectedCoupon] = props.hooks;
    const [coupons, setCoupons] = useState<CouponItem[]>([]);
    function handleClose() {
        setOpen(false);
    }
    function handleAccept() {
        accept(selectedCoupon?.id);
        handleClose();
    }
    return (
        <div className="AddDialog">
            <Dialog dir="rtl" open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"עריכת פרטים"}
                </DialogTitle>
                <DialogContent>
                    <br />
                    <TextField dir="rtl" label="כותרת" required error={false} /><br /><br />
                    <TextField label="Description" /><br /><br />
                    <TextField label="Category" /><br /><br />
                    <TextField label="Start Date" /><br /><br />
                    <TextField label="End Date" /><br /><br />
                    <FormControl>
                        <InputLabel htmlFor="adornment-price">Price</InputLabel>
                        <OutlinedInput
                         id="adornment-price"
                         startAdornment={<InputAdornment position="start">₪</InputAdornment>}
                         label="Price"
                         placeholder="Price"
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

export default AddDialog;
