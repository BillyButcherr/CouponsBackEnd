import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";
import CouponContainer from "../../../ContentArea/HomeArea/CouponContainer/CouponContainer";
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
