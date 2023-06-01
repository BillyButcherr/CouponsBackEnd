import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import "./Signup.css";

function Signup(): JSX.Element {
    return (
        <div className="Signup">
            <ContentContainer>
                <Content>
                    <h2>הרשמה</h2>
                    <form id="myForm">
                        <label htmlFor="first-name">שם פרטי</label>
                        <input type="text" id="first-name" placeholder="שם פרטי" />
                        <label htmlFor="last-name">שם משפחה</label>
                        <input type="text" id="last-name" placeholder="שם משפחה" />
                        <label htmlFor="email">כתובת דוא"ל</label>
                        <input type="email" id="email" placeholder="אימייל" />
                        <label htmlFor="password">סיסמה</label>
                        <input type="password" id="password" placeholder="סיסמה" />
                        {/* <label htmlFor="password-again">הקלד סיסמה שנית</label> */}
                        <input type="password" id="password-again" placeholder="הקלד שנית" />
                        <input type="submit" value="צור חשבון" />
                    </form>
                </Content>
            </ContentContainer>
        </div>
    );
}

export default Signup;
