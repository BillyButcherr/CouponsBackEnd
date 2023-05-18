import Navbar from "../Navbar/Navbar";
import "./Header.css";

function Header(props:any): JSX.Element {
    return (
        <header className="Header">
			<Navbar myHook={props.myHook}/>
        </header>
    );
}

export default Header;
