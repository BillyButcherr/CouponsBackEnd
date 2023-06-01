import Footer from "../FooterArea/Footer/Footer";
import Header from "../HeaderArea/Header/Header";
import Routing from "../Routing/Routing";
import "./Layout.css";

function Layout(): JSX.Element {
  return (
    <div className="Layout">
      <Header/>
      <Routing/>
      <Footer />
    </div>
  );
}

export default Layout;
