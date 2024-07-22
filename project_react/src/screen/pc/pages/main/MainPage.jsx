import React from "react";
import Footer from "../Footer";
import Header from "../Header";
import NavSection from "./section/NavSection";
import StockSection from "./section/StockSection";
import IndexSection from "./section/IndexSection";

const MainPage = () => {
    return(
        <div>
            <Header/>
            <NavSection/>
            <StockSection/>
            <IndexSection/>
            <Footer/>
        </div>
    );
};

export default MainPage;