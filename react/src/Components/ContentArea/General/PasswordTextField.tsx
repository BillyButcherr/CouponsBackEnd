import InputAdornment from '@mui/material/InputAdornment';
import MuiTextField from '@mui/material/TextField';
import { styled } from '@mui/material/styles';
import Visibility from '@mui/icons-material/Visibility';
import { VisibilityOff } from '@mui/icons-material';
import { IconButton } from '@mui/material';
import Lock from "@mui/icons-material/Lock";


const TextField = styled(MuiTextField)(({ theme }) => ({
    '& .MuiOutlinedInput-root': {
        paddingLeft: 0,
        paddingRight: 0,
    },
    '& .MuiInputBase-input':{
        paddingRight: '14px'
    }
}));

export default function PasswordTextField(props: any) {
    const [showPassword, setShowPassword, setPassword, passwordError] = props.hooks;
    return (
        <TextField fullWidth
            type={showPassword ? "text" : "password"}
            InputProps={{
                startAdornment: <InputAdornment position="start"><IconButton tabIndex={-1}><Lock /></IconButton></InputAdornment>,
                endAdornment: <InputAdornment sx={{ padding: 0 }}
                    onClick={() => setShowPassword(!showPassword)}
                    position="end">
                    <IconButton tabIndex={-1}>
                        {showPassword ? <Visibility /> : <VisibilityOff />}
                    </IconButton>
                </InputAdornment>
            }}
            placeholder="סיסמה"
            error={passwordError}
            helperText={passwordError && 'סיסמה לא תקינה'}
            onChange={(e) => setPassword(e.target.value)}
        ></TextField>
    );
}
