import { Navigate, Route, Routes } from "react-router-dom";
import "./Routing.css";
import Home from "../../PagesArea/HomeArea/Home/Home";
import Signup from "../../PagesArea/AuthArea/Signup/Signup";
import Login from "../../PagesArea/AuthArea/Login/Login";
import PageNotFound from "../../PagesArea/PageNotFoundArea/PageNotFound/PageNotFound";
import Admin from "../../PagesArea/AdminArea/Admin/Admin";
import Profile from "../../PagesArea/ProfileArea/Profile/Profile";
import CompanyPage from "../../PagesArea/CompanyArea/CompanyPage/CompanyPage";
import CustomerPage from "../../PagesArea/CustomerArea/CustomerPage/CustomerPage";
import Logout from "../../PagesArea/AuthArea/Logout/Logout";
import PurchasePage from "../../PagesArea/CustomerArea/PurchasePage/PurchasePage";
import CouponPage from "../../PagesArea/ViewerArea/CouponPage/CouponPage";
import LoginV2 from "../../PagesArea/AuthArea/LoginV2/LoginV2";
import AboutPage from "../../PagesArea/AboutArea/About/AboutPage";

function Routing(): JSX.Element {
    return (
        <div className="Routing">
            <Routes>
                <Route path="/home" element={<Home />} />
                <Route path="/login" element={<LoginV2/>} />
                <Route path="/signup" element={<Signup />} />
                <Route path="/logout" element={<Logout/>} />


                <Route path="/admin" element={<Admin />} />
                <Route path="/companies" element={<CompanyPage />} />
                <Route path="/customers" element={<CustomerPage />} />
                <Route path="/about" element={<AboutPage />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/profile/purchases/:id" element={<PurchasePage/>} />
                <Route path="/coupons/:id" element={<CouponPage/>} />

                
                <Route path="" element={<Navigate to="/home" />} />
                <Route path="/*" element={<PageNotFound />} />
            </Routes>
        </div>
    );
}

export default Routing;
