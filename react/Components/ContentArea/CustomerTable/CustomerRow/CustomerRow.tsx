import Customer from "../../../../Models/Customer/Customer";
import "./CustomerRow.css";

function CustomerRow(props: any): JSX.Element {
    const [customerModal, setCustomerModal, customerInfo, setCustomerInfo] = props.hooks;
    const [customerItems, setCustomerItems] = props.tableHooks;

    function editAction(){
        setCustomerInfo(props.info);
        setCustomerModal(true);
        // alert(props.id);
    }
    function deleteAction(){
        const updatedCustomerItems = customerItems.filter((item:Customer) => {
            return item.id !== props.info.id;
        });
        setCustomerItems(updatedCustomerItems);
    }

    return (
        <tr className="CustomerItem">
            <td>{props.info.id}</td>
            <td>{props.info.firstName}</td>
            <td>{props.info.lastName}</td>
            <td>{props.info.email}</td>
            <td>{props.info.password}</td>
            <td>
                <span className="material-symbols-outlined icon-hover edit" onClick={editAction}>edit</span>
                <span className="material-symbols-outlined icon-hover delete" onClick={deleteAction}>delete</span>
            </td>
        </tr>
    );
}

export default CustomerRow;
