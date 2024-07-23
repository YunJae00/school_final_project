import React from "react";
import Header from "../Header";
import Footer from "../Footer";
import StockListSection from "./section/StockListSection";

const StockListPage = () => {
    return(
        <div>
            <Header/>
            <StockListSection/>
            <Footer/>
        </div>
    );
};

export default StockListPage;