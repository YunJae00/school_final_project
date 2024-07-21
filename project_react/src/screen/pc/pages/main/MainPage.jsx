import React from "react";
import Footer from "../Footer";
import Header from "../Header";
import NavSection from "./NavSection";
import StockSection from "./StockSection";

const MainPage = () => {
    return(
        <div>
            <Header/>
            <NavSection/>
            <StockSection/>
            <Footer/>
        </div>
    );
};

export default MainPage;