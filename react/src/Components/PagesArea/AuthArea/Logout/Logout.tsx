import { Navigate } from "react-router-dom";
import "./Logout.css";
import authService from "../../../../Services/AuthService";

function Logout(): JSX.Element {
    authService.logout();
    return (
        <div className="Logout">
			<Navigate to="/home" />
        </div>
    );
}

export default Logout;
