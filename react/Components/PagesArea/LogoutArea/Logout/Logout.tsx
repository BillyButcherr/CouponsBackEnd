import { Navigate } from "react-router-dom";
import "./Logout.css";
import authService from "../../../../Services/AuthService";

function Logout(props:any): JSX.Element {
    const setLoggedIn = props.myHook;
    
    authService.logout();
    setLoggedIn('false');
    return (
        <div className="Logout">
			<Navigate to="/home" />
        </div>
    );
}

export default Logout;
