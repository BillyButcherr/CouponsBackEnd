import { NavLink } from "react-router-dom";
import "./Navbar.css";
import { useState } from "react";

function Navbar(props:any): JSX.Element {  
    const loggedIn = props.myHook;
    function isLogged() {
        return loggedIn == 'true';
        // return sessionStorage.getItem('authToken') != null;
    }
    return (
        <nav className="Navbar">
            <NavLink to="/home" id="logo">חופשות</NavLink>

            <ul className="nav-links">
                <li><NavLink to="/admin" className="active">מנהל מערכת</NavLink></li>
                <li><NavLink to="/home">קופונים</NavLink></li>
                <li><NavLink to="/home">חברות</NavLink></li>
                <li><NavLink to="/home">לקוחות</NavLink></li>
                <li><NavLink to="/home">אודות</NavLink></li>
                <li><NavLink to="/logout">התנתקות</NavLink></li>
                
            </ul>
            {!isLogged() &&
                <div className="account-box">
                    <NavLink to="/login" className="login-box">
                        <span className="material-symbols-outlined login-symbol">login</span>
                        <span>כניסה</span>
                    </NavLink>
                    <NavLink to="/signup" className="signup-box">
                        <span className="material-symbols-outlined signup-symbol">person</span>
                        <span>הרשמה</span>
                    </NavLink>
                </div>
            }
            {isLogged() &&
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
