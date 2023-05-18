import Customer from "../../../../Models/Customer/Customer";
import Modal from "../Modal/Modal";
import "./CustomerUpdateForm.css";

function CustomerUpdateForm(props: any): JSX.Element {
    const [modal, setModal, customerInfo, setCustomerInfo, updateTable] = props.hooks;

    function submit(e: any) {
        e.preventDefault();

        const customerId = customerInfo.id;
        const customerFirstName = e.target[0].value;
        const customerLastName = e.target[1].value;
        const customerEmail = e.target[2].value;
        const customerPassword = e.target[3].value;
        const customerPasswordAgain = e.target[4].value;

        const updatedInfo = new Customer(customerId, customerFirstName, customerLastName, customerEmail, customerPassword);
        setCustomerInfo(updatedInfo);
        updateTable(updatedInfo);
    }
    return (
        <Modal firstHook={modal} secondHook={setModal}>
            <div className="CustomerUpdateForm">
                <h3>פרטי לקוח</h3>
                <form onSubmit={submit}>
                    <label htmlFor="first-name">שם הלקוח</label>
                    <input type="text" id="first-name" placeholder="שם פרטי" defaultValue={customerInfo?.firstName} />
                    {/* <label htmlFor="last-name">שם משפחה</label> */}
                    <input type="text" id="last-name" placeholder="שם משפחה" defaultValue={customerInfo?.lastName} />
                    <label htmlFor="email">כתובת דוא"ל</label>
                    <input type="email" id="email" placeholder="אימייל" defaultValue={customerInfo?.email} />
                    <label htmlFor="password">סיסמה</label>
                    <input type="password" id="password" placeholder="סיסמה" />
                    {/* <label htmlFor="password-again">הקלד סיסמה שנית</label> */}
                    <input type="password" id="password-again" placeholder="הקלד שנית" />
                    <input type="submit" value="עדכון פרטים" />
                </form>
            </div>
        </Modal>

    );
}

export default CustomerUpdateForm;
