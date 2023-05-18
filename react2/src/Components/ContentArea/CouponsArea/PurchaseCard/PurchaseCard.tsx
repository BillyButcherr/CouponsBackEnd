import { useNavigate } from "react-router-dom";
import "./PurchaseCard.css";
interface CouponProps{
    id:string;
    title:string;
    description:string;
    image:string;
}
function PurchaseCard(props:any): JSX.Element {
    const navigate = useNavigate();

    function getInfo(){
        navigate("purchases/" + props.info.id);
        // alert("hello");
    }
    return (
        <article className="PurchaseCard" onClick={getInfo}>
            <figure className="article-image">
                <img src={props.info.image} />
            </figure>
            <div className="article-content">
                <h5>{props.info.title}</h5>
                <p>{props.info.description}</p>
            </div>
        </article>
    );
}

export default PurchaseCard;
