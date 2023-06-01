import { NavLink } from "react-router-dom";
import "./Navbar.css";
import { useEffect, useState } from "react";
import { authStore } from "../../../../Store/AuthState";

function Navbar(): JSX.Element {
    const [isLoggedIn, setLoggedIn] = useState(false);
    useEffect(() => {
        const unsubscribe = authStore.subscribe(() => {
            setLoggedIn(!authStore.getState().authData.token === false);//if there is a token then set isLoggedIn to true, otherwise false.
        });
        return unsubscribe;
    }, []);
    return (
        <nav className="Navbar">
            <NavLink to="/home" id="logo">חופשות</NavLink>

            <ul className="nav-links">
                <li><NavLink to="/admin" className="active">מנהל מערכת</NavLink></li>
                <li><NavLink to="/home">קופונים</NavLink></li>
                <li><NavLink to="/home">חברות</NavLink></li>
                <li><NavLink to="/home">לקוחות</NavLink></li>
                <li><NavLink to="/about">אודות</NavLink></li>
                {isLoggedIn && <li><NavLink to="/logout">התנתקות</NavLink></li>}
            </ul>
            {!isLoggedIn &&
                <div className="account-box">
                    <NavLink to="/login" className="login-box">
                        <span className="material-symbols-outlined login-symbol">person</span>
                        <span>כניסה</span>
                    </NavLink>
                    {/* <NavLink to="/signup" className="signup-box">
                        <span className="material-symbols-outlined signup-symbol">person</span>
                        <span>הרשמה</span>
                    </NavLink> */}
                </div>
            }
            {isLoggedIn &&
                <div className="account-box">
                    <NavLink to="/profile" className="signup-box">
                        <span className="material-symbols-outlined signup-symbol">person</span>
                        <span>החשבון שלי</span>
                    </NavLink>
                </div>
            }
        </nav>
    );
}

export default Navbar;
