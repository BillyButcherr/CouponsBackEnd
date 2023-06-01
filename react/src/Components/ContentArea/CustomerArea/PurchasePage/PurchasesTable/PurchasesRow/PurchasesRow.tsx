import "./PurchasesRow.css";

function PurchasesRow(props: any): JSX.Element {
    return (
        <tr className="CustomerItem">
            <td>{props.a}</td>
            <td>{props.b}</td>
        </tr>
    );
}

export default PurchasesRow;
