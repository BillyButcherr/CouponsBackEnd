import Content from "../../../ContentArea/Content/Content";
import ContentContainer from "../../../ContentArea/ContentContainer/ContentContainer";
import CouponContainer from "../../../ContentArea/CouponsArea/CouponContainer/CouponContainer";
import "./Home.css";

function Home(): JSX.Element {
    return (
        <div className="Home">
            <ContentContainer>
                <Content title="יעדים מומלצים">
                    <CouponContainer/>
                </Content>
                <Content title="חופשות"></Content>
            </ContentContainer>
        </div>
    );
}

export default Home;
