import { useEffect, useState } from "react";
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, FormControl, InputAdornment, InputLabel, OutlinedInput, TextField } from "@mui/material";
import { useForm } from "react-hook-form";
import Company from "../../../../../Models/Company/Company";
import Customer from "../../../../../Models/Customer/Customer";

function AddCustomerDialog(props: any): JSX.Element {
    const [open, setOpen, accept] = props.hooks;
    
    const form = useForm<FormValues>();
    const {register, control, handleSubmit, formState} = form;
    const {errors} = formState;
    type FormValues = {
        firstName:string;
        lastName:string;
        email:string;
        password:string;
    }
    function handleClose() {
        setOpen(false);
    }
    function handleAccept(data:FormValues) {
        accept(new Customer("", data.firstName, data.lastName, data.email, data.password));
        handleClose();
    }
    return (
        <div className="AddCustomerDialog">
            <Dialog dir="rtl" open={open} onClose={handleClose} aria-labelledby="alert-dialog-title" aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">
                    {"חברה חדשה"}
                </DialogTitle>
                <DialogContent>
                    <br />
                    <TextField label="First Name" {...register("firstName",{
                        required:"first name is required."
                    })} helperText={errors.firstName?.message}/>
                    <br /><br />
                    <TextField label="Last Name" {...register("lastName",{
                        required:"last name is required."
                    })} helperText={errors.lastName?.message}/>
                    <br /><br />
                    <TextField label="Email" {...register("email",{
                        required:"email is required."
                    })} helperText={errors.email?.message}/>
                    <br /><br />
                    <TextField label="Password" {...register("password",{
                        required:"password is required."
                    })} helperText={errors.password?.message}/>
                    <br /><br />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} autoFocus>ביטול</Button>
                    <Button onClick={handleSubmit(handleAccept)}>אישור</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default AddCustomerDialog;
