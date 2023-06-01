import authService from "../../../../Services/AuthService";
import Content from "../../../ContentArea/General/Content/Content";
import ContentContainer from "../../../ContentArea/General/ContentContainer/ContentContainer";

function AboutPage(): JSX.Element {
    return (
        <ContentContainer>
            <Content>
                <div className="AboutPage">
                    {/* <button onClick={() => alert(authService.isLogged())}>test</button> */}
                    <h1>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Minima, quae.</h1>
                    <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Itaque aspernatur dolore temporibus in. Quo quae optio ex voluptas officiis minima accusantium. Corrupti magni similique facilis laudantium! Beatae illum fuga minus.</p>
                    <p>Lorem, ipsum dolor sit amet consectetur adipisicing elit. Optio, aspernatur aliquid unde quam dolorum molestiae veritatis quia tenetur doloribus, numquam fugiat eveniet accusamus cumque quaerat fugit tempora modi soluta magni eligendi libero non quos eius? Velit nostrum nobis dolore rem error accusantium sit enim veniam itaque vero tempora minus corrupti dolorem similique voluptate laborum, totam consequuntur praesentium, pariatur exercitationem. Esse necessitatibus, eos officia porro accusamus sint neque inventore id veritatis recusandae eaque suscipit nihil debitis quasi tenetur natus, fugit reprehenderit?</p>
                    <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Adipisci ipsa minima velit ratione quis quasi, cumque vero distinctio esse ad ea nulla corporis eos ducimus omnis? Quod eius non laborum ex hic corrupti consequatur veniam molestiae soluta, dolor ut laboriosam itaque, mollitia facere necessitatibus excepturi aut delectus reiciendis quis dignissimos iste voluptatem labore. Obcaecati vitae cupiditate eaque enim nemo accusantium corrupti dolorum quae, minus, aperiam amet voluptas. Ipsum voluptas repudiandae, dolores error quod neque rem, beatae quae incidunt sapiente tempore.</p>
                </div>
            </Content>
        </ContentContainer>

    );
}

export default AboutPage;
