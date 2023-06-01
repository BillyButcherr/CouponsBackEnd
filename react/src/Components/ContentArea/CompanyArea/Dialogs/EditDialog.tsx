import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, MenuItem, OutlinedInput, TextField } from "@mui/material";
import CouponItem, { Category } from "../../../../Models/Coupon/CouponItem";
import authService from "../../../../Services/AuthService";

function EditDialog(props: any): JSX.Element {
    const authData = authService.getAuthData();

    const [open, setOpen, accept, selectedCoupon] = props.hooks;

    const [title, setTitle] = useState<string>(selectedCoupon?.title);
    const [description, setDescription] = useState<string>(selectedCoupon?.description);
    const [category, setCategory] = useState<Category>(Number.parseInt(Category[selectedCoupon?.category]));
    const [startDate, setStartDate] = useState<string>(selectedCoupon?.startDate);
    const [endDate, setEndDate] = useState<string>(selectedCoupon?.endDate);
    const [price, setPrice] = useState<number>(selectedCoupon?.price);
    const [amount, setAmount] = useState<number>(selectedCoupon?.amount);

    function handleClose() {
        setOpen(false);
    }
    function getToday(): string {
        const today = new Date();
        return today.toISOString().substring(0, 10);
    }
    function getTenDaysAhead(): string {
        const today = new Date();
        const tenDaysAhead = new Date(today.setDate(today.getDate() + 10));
        return tenDaysAhead.toISOString().substring(0, 10);
    }
    function handleAccept() {
        const coupon = new CouponItem(selectedCoupon.id, selectedCoupon.company, category,
            title, description, startDate, endDate, amount, price, selectedCoupon?.image);
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
                    <TextField dir="rtl" label="כותרת" defaultValue={selectedCoupon?.title} onChange={(e) => setTitle(e.target.value)} /><br /><br />
                    <TextField label="Description" multiline rows={3} defaultValue={selectedCoupon?.description} onChange={(e) => setDescription(e.target.value)} /><br /><br />
                    <TextField label="Category" select defaultValue={Category[selectedCoupon?.category]} onChange={(e) => {
                        setCategory(Number.parseInt(e.target.value));
                    }}>
                        <MenuItem key={0} value={Category.Food}>{Category[0]}</MenuItem>
                        <MenuItem key={1} value={Category.Electricity}>{Category[1]}</MenuItem>
                        <MenuItem key={2} value={Category.Restaurant}>{Category[2]}</MenuItem>
                        <MenuItem key={3} value={Category.Vacation}>{Category[3]}</MenuItem>
                    </TextField>
                    <br /><br />
                    <input type="date" name="startDate" defaultValue={getToday()} onChange={(e) => setStartDate(e.target.value)} />
                    <br /><br />
                    <input type="date" name="endDate" defaultValue={getTenDaysAhead()} onChange={(e) => setEndDate(e.target.value)} />
                    <br /><br />
                    <FormControl>
                        <InputLabel htmlFor="adornment-price">Price</InputLabel>
                        <OutlinedInput
                            id="adornment-price"
                            startAdornment={<InputAdornment position="start">₪</InputAdornment>}
                            label="Price"
                            placeholder="Price"
                            defaultValue={selectedCoupon?.price}
                            onChange={(e) => setPrice(Number.parseInt(e.target.value))}
                        />
                    </FormControl>
                    <br /><br />
                    <TextField label="Amount" defaultValue={selectedCoupon?.amount} onChange={(e) => setAmount(Number.parseInt(e.target.value))} /><br /><br />
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

