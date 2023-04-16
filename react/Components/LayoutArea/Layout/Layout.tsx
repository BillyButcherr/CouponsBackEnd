import { useState } from "react";
import Content from "../../ContentArea/Content/Content";
import ContentContainer from "../../ContentArea/ContentContainer/ContentContainer";
import Footer from "../FooterArea/Footer/Footer";
import Header from "../HeaderArea/Header/Header";
import Routing from "../Routing/Routing";
import "./Layout.css";

function Layout(): JSX.Element {
  const[loggedIn, setLoggedIn] = useState<string>('false');
  return (
    <div className="Layout">
      <Header myHook={loggedIn}/>
      <Routing myHook={setLoggedIn}/>
      <Footer />
    </div>
  );
}

export default Layout;
