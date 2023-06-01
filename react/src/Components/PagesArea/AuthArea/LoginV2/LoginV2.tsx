import AuthService from "../../../../Services/AuthService";
import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import LoginData from "../../../../Models/LoginData/LoginData";
import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import Person from "@mui/icons-material/Person";
import Lock from "@mui/icons-material/Lock";


// import "./Login.css";
import { useNavigate } from "react-router-dom";
import { TextField, InputAdornment, Button, IconButton, styled, makeStyles, Box } from "@mui/material";
import { useState } from "react";
import authService from "../../../../Services/AuthService";
import MuiTextField from "@mui/material/TextField";
import PasswordTextField from "../../../ContentArea/General/PasswordTextField";

function LoginV2(): JSX.Element {
    const navigate = useNavigate();

    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const [showPassword, setShowPassword] = useState(false);

    const [emailError, setEmailError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);

    function isValidInput(): boolean {
        let isValid = true;
        if (!email.includes("@") || !email.includes(".")) {
            setEmailError(true);
            isValid = false;
        } else {
            setEmailError(false);
        }
        if (password.length <= 2) {
            setPasswordError(true);
            isValid = false;
        } else {
            setPasswordError(false);
        }
        return isValid;
    }
    function attempLogin() {
        if (!isValidInput()) return;
        AuthService.attemptLogin(new LoginData(email, password))
        .then(() => {
            navigate('/profile');
        })
        .catch(err => {
            setEmailError(true);
            setPasswordError(true);
            console.log(err.message);
        });
    }
    // const myTextField = styled(MuiTextField)(({theme}) => ({
    //     '& .MuiOutlinedInput-root':{
    //         paddingLeft : 0
    //     },
    //     '& .MuiInputAdornment-root':{
    //         padding : 0
    //     }
    // }))
    return (
        <div className="LoginV2">
            <ContentContainer>
                <Content>
                    <Box sx={{width:"20vw"}}>
                        <h2>התחברות למערכת</h2>
                        <br />
                        <TextField fullWidth
                            InputProps={{
                                startAdornment: <InputAdornment position="start">
                                    <span><IconButton tabIndex={-1}><Person /></IconButton></span>
                                </InputAdornment>,
                            }}
                            placeholder='דוא"ל'
                            error={emailError}
                            helperText={emailError && 'דוא"ל לא תקין'}
                            onChange={(e) => setEmail(e.target.value)}
                        ></TextField>
                        <br /><br />
                        <PasswordTextField hooks={[showPassword, setShowPassword, setPassword, passwordError]} />
                        <br /><br />
                        <Button onClick={attempLogin}>התחברות</Button>
                    </Box>

                </Content>
            </ContentContainer>
        </div>
    );
}

export default LoginV2;