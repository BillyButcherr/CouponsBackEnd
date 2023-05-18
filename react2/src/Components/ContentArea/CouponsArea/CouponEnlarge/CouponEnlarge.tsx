import { useNavigate } from "react-router-dom";
import "./CouponEnlarge.css";
interface CouponProps {
    id: string;
    title: string;
    description: string;
    image: string;
}
function CouponEnlarge(props: any): JSX.Element {
    return (
        <div className="CouponEnlarge">
            <article className="coupon">
                <figure className="article-image">
                    <img src={props.info?.image} />
                </figure>
                <div className="article-content">
                    <h5>{props.info?.title}</h5>
                    <p>{props.info?.description}</p>
                </div>
            </article>
        </div>

    );
}

export default CouponEnlarge;
