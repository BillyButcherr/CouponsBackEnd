import { useNavigate } from "react-router-dom";
import "./Coupon.css";
interface CouponProps{
    id:string;
    title:string;
    description:string;
    image:string;
}
function Coupon(props:any): JSX.Element {
    const navigate = useNavigate();

    function getInfo(){
        navigate("/coupons/" + props.info.id);
        // alert("hello");
    }
    return (
        <article className="coupon" onClick={getInfo}>
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

export default Coupon;
