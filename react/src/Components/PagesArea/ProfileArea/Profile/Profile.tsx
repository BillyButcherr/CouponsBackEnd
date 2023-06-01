import authService from "../../../../Services/AuthService";
import Admin from "../../AdminArea/Admin/Admin";
import CompanyPage from "../../CompanyArea/CompanyPage/CompanyPage";
import CustomerPage from "../../CustomerArea/CustomerPage/CustomerPage";
import "./Profile.css";

function Profile(): JSX.Element {
    function clientType(){
        // return sessionStorage.getItem('clientType');
        return authService.getAuthData().clientType;
    }
    return (
        <div className="Profile">
            {clientType() == 'Customer' && <CustomerPage/> }
			{clientType() == 'Company' && <CompanyPage/> }
			{clientType() == 'Admin' && <Admin/> }
        </div>
    );
}

export default Profile;
