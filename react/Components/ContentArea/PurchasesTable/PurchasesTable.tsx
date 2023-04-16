import "./PurchasesTable.css";
import PurchasesRow from "./PurchasesRow/PurchasesRow";

function PurchasesTable(props: any): JSX.Element {
    // const [customerItems, setCustomerItems] = useState<Customer[]>([]);
    // useEffect(() => {
    //     (new AdminService).getAllCustomers()
    //         .then(customers => setCustomerItems(customers))
    //         .catch(err => console.log(err));
    // }, []);
    return (
        <table className="PurchasesTable">
            <thead>
                <tr>
                    <th>פירוט רכישה</th>
                    <th></th>
                    {/* <th>תיאור</th> */}
                    {/* <th>חברה</th>
                    <th>תאריך סיום</th>
                    <th>מחיר</th> */}

                </tr>
            </thead>
            <tbody>
                {/* {customerItems.map(item => ( */}
                <PurchasesRow key={1} a={"כותרת"} b={props.purchase?.title} info={props.purchase} />
                <PurchasesRow key={2} a={"תיאור"} b={props.purchase?.description} info={props.purchase} />
                <PurchasesRow key={3} a={"חברה"} b={props.purchase?.company.name} info={props.purchase} />
                <PurchasesRow key={4} a={"תאריך סיום"} b={props.purchase?.endDate} info={props.purchase} />
                <PurchasesRow key={5} a={"מחיר"} b={"₪" + props.purchase?.price} info={props.purchase} />
                {/* // ))} */}
            </tbody>
        </table>
    );
}

export default PurchasesTable;
