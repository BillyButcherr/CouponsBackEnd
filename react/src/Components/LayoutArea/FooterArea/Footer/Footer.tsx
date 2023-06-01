import { Snackbar, Alert, Slide } from "@mui/material";
import "./Footer.css";
import { useEffect, useState } from "react";
import { notificationStore } from "../../../../Store/NotificationState";

function Footer(): JSX.Element {
    const [snackbarOpen, setSnackBarOpen] = useState(false);
    const [snackbarMessage, setSnackBarMessage] = useState<string>("");

    useEffect(() => {
        const unsubscribe = notificationStore.subscribe(() => {
            setSnackBarOpen(notificationStore.getState().notificationData.open);
            setSnackBarMessage(notificationStore.getState().notificationData.message);
        });
        return unsubscribe;
    }, []);
    function transitionDirection(props:any){
        return <Slide {...props} direction="up"/>
    }
    return (
        <>
            <Snackbar anchorOrigin={{ horizontal: "right", vertical: "bottom" }} open={snackbarOpen}
                autoHideDuration={5000} onClose={() => setSnackBarOpen(false)} TransitionComponent={transitionDirection}>
                <Alert severity="success">{snackbarMessage}</Alert>
            </Snackbar>
            <footer className="footer">

            </footer>
        </>

    );
}

export default Footer;
