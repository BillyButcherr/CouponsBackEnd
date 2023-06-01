import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import { useForm } from "react-hook-form";

function EditCustomerDialog(props: any): JSX.Element {
    const [open, setOpen, accept, selectedCustomer] = props.hooks;
    const [firstName, setFirstName] = useState<string>("");
    const [lastName, setLastName] = useState<string>("");
    const [email, setEmail] = useState<string>("");
    useEffect(() => {
        loadDefaultValues();
    }, []);
    
    function handleClose() {
        setOpen(false);
    }
    function loadDefaultValues(){
        setFirstName(selectedCustomer?.firstName);
        setLastName(selectedCustomer?.lastName);
        setEmail(selectedCustomer?.email);
    }
    function handleValidation(){
        
    }
    function handleAccept() {
        const customer = {...selectedCustomer};
        customer.firstName = firstName;
        customer.lastName = lastName;
        customer.email = email;
        
        if(!firstName){
            customer.firstName = selectedCustomer?.firstName;
        }
        if(!lastName){
            customer.lastName = selectedCustomer?.lastName;
        }
        if(!email)
            customer.email = selectedCustomer?.email;
        
        accept(customer.id, customer.firstName, customer.lastName, customer.email);
        handleClose();
    }
    return (
        <div className="EditCustomerDialog">
            <Dialog dir="rtl" open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {'עריכת פרטי לקוח'}
                </DialogTitle>
                <DialogContent>
                    <br />
                    <TextField label="First Name"
                    defaultValue={selectedCustomer?.firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                    />
                    <br /><br />
                    <TextField label="Last Name"
                    defaultValue={selectedCustomer?.lastName}
                    onChange={(e) => setLastName(e.target.value)}
                    />
                    <br /><br />
                    <TextField label="Email"
                    defaultValue={selectedCustomer?.email}
                    onChange={(e) => setEmail(e.target.value)}
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

export default EditCustomerDialog;

