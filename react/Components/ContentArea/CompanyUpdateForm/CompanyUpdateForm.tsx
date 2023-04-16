import { SyntheticEvent } from "react";
import "./CompanyUpdateForm.css";
import Company from "../../../Models/Company/Company";

function CompanyUpdateForm(props:any): JSX.Element {
    const [modal, setModal, companyInfo, setCompanyInfo] = props.hooks;

    function submit(e:any){
        e.preventDefault();

        const companyId = companyInfo.id;
        const companyName = e.target[0].value;
        const companyEmail = e.target[1].value;
        const companyPassword = e.target[2].value;
        const companyPasswordAgain = e.target[3].value;

        const updatedInfo = new Company(companyId, companyName, companyEmail, companyPassword);
        setCompanyInfo(updatedInfo);
        
        // alert(e.target[1].value);
        // alert(companyInfo.email);
    }
    // const [firstName, lastName, email] = props?.info;
    return (
        <div className="CustomerUpdateForm">
            <h3>פרטי חברה</h3>
            <form onSubmit={submit}>
                <label htmlFor="company-name">שם החברה</label>
                <input type="text" id="company-name" placeholder="שם החברה" defaultValue={companyInfo.name} />
                <label htmlFor="email">כתובת דוא"ל</label>
                <input type="email" id="email" placeholder="אימייל" defaultValue={companyInfo.email}/>
                <label htmlFor="password">סיסמה</label>
                <input type="password" id="password" placeholder="סיסמה"/>
                {/* <label htmlFor="password-again">הקלד סיסמה שנית</label> */}
                <input type="password" id="password-again" placeholder="הקלד שנית" />
                <input type="submit" value="עדכון פרטים"/>
            </form>
        </div>
    );
}

export default CompanyUpdateForm;
