import AuthService from "../../../../Services/AuthService";
import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import LoginData from "../../../../Models/LoginData/LoginData";
import "./Login.css";
import { useNavigate } from "react-router-dom";

function Login(props: any): JSX.Element {
    const setLoggedIn = props.myHook;
    const navigate = useNavigate();

    function attempLogin(event: any) {
        event.preventDefault();
        const email = event.target[0].value;
        const password = event.target[1].value;

        AuthService.attemptLogin(new LoginData(email, password)).then(() => {
            setLoggedIn('true'); 
            navigate('/profile');
        })
        .catch(err => console.log(err.message));
        // authService.attemptLogin(new LoginData(email, password)).then(role => {
        //     if(role == "company"){
        //         setLoggedIn('true');
        //     } else if(role == "admin") {
        //         setLoggedIn('true');
        //     }
        //     navigate('/profile');
        // });
    }

    return (
        <div className="Login">
            <ContentContainer>
                <Content>
                    <h2>התחברות למערכת</h2>
                    <form onSubmit={attempLogin}>
                        <input type="email" id="email" placeholder='דוא"ל' />
                        <input type="password" id="password" placeholder="סיסמה" />
                        <input type="submit" id="submit" value="התחברות" />
                    </form>
                </Content>
            </ContentContainer>
        </div>
    );
}

export default Login;
