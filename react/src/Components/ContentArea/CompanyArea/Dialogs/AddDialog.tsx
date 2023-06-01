import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, MenuItem, OutlinedInput, TextField } from "@mui/material";
import CouponItem, { Category } from "../../../../Models/Coupon/CouponItem";
import Company from "../../../../Models/Company/Company";
import CouponDTO from "../../../../Models/Coupon/CouponDTO";

import "./AddDialog.css";
import authService from "../../../../Services/AuthService";
function AddDialog(props: any): JSX.Element {
    const authData = authService.getAuthData();

    const [open, setOpen, accept] = props.hooks;
    
    const [title, setTitle] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [category, setCategory] = useState<Category>(Category.Food);
    const [startDate, setStartDate] = useState<string>(getToday());
    const [endDate, setEndDate] = useState<string>(getTenDaysAhead());
    const [price, setPrice] = useState<number>(2400);
    const [amount, setAmount] = useState<number>(15);

    function getToday(): string {
        const today = new Date();
        // const tenDaysAhead = new Date(today.setDate(today.getDate() + 10));
        return today.toISOString().substring(0, 10);
    }
    function getTenDaysAhead(): string {
        const today = new Date();
        const tenDaysAhead = new Date(today.setDate(today.getDate() + 10));
        return tenDaysAhead.toISOString().substring(0, 10);
    }

    function handleClose() {
        setOpen(false);
    }
    function handleAccept() {
        const coupon = new CouponDTO(authData.clientId, category, title, description, startDate, endDate, amount, price, "");
        // const coupon = new CouponDTO("2", Category.Vacation, "title", "description", "2023-05-23", "2025-05-23", 1400, 5, "url");
        accept(coupon);
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
                    <TextField dir="rtl" label="כותרת" required error={false} onChange={(e) => setTitle(e.target.value)}/><br /><br />
                    <TextField label="Description" multiline rows={3} onChange={(e) => setDescription(e.target.value)} /><br /><br />
                    <TextField label="Category" select defaultValue={0} onChange={(e) => setCategory(Number.parseInt(e.target.value))}>
                        <MenuItem key={0} value={Category.Food}>{Category[0]}</MenuItem>
                        <MenuItem key={1} value={Category.Electricity}>{Category[1]}</MenuItem>
                        <MenuItem key={2} value={Category.Restaurant}>{Category[2]}</MenuItem>
                        <MenuItem key={3} value={Category.Vacation}>{Category[3]}</MenuItem>
                    </TextField>
                    <br /><br />
                    <input type="date" name="startDate" defaultValue={getToday()} onChange={(e) => setStartDate(e.target.value)}/>
                    <div>עד</div>
                    <input type="date" name="endDate" defaultValue={getTenDaysAhead()} onChange={(e) => setEndDate(e.target.value)}/>
                    <br /><br />
                    <FormControl>
                        <InputLabel htmlFor="adornment-price">Price</InputLabel>
                        <OutlinedInput
                         id="adornment-price"
                         startAdornment={<InputAdornment position="start">₪</InputAdornment>}
                         label="Price"
                         placeholder="Price"
                         defaultValue={2400}
                         onChange={(e) => setPrice(Number.parseInt(e.target.value))}
                        />
                    </FormControl>
                    <br /><br />
                    <TextField label="Amount" defaultValue={15} onChange={(e) => setAmount(Number.parseInt(e.target.value))} /><br /><br />
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
