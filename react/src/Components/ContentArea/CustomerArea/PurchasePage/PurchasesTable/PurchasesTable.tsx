import "./PurchasesTable.css";
import PurchasesRow from "./PurchasesRow/PurchasesRow";

function PurchasesTable(props: any): JSX.Element {
    return (
        <table className="PurchasesTable">
            <thead>
                <tr>
                    <th>פירוט רכישה</th>
                    <th></th>  
                </tr>
            </thead>
            <tbody>
                <PurchasesRow key={1} a={"כותרת"} b={props.purchase?.title} />
                <PurchasesRow key={2} a={"תיאור"} b={props.purchase?.description}/>
                <PurchasesRow key={3} a={"חברה"} b={props.purchase?.company.name}/>
                <PurchasesRow key={4} a={"תאריך סיום"} b={props.purchase?.endDate} />
                <PurchasesRow key={5} a={"מחיר"} b={"₪" + props.purchase?.price} />
            </tbody>
        </table>
    );
}

export default PurchasesTable;
