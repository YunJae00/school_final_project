import React from "react";
import Header from "../Header";
import Footer from "../Footer";
import StockDetailSection from "./section/StockDetailSection";

const StockDetailPage = () => {
    return(
        <div>
            <Header/>
            <StockDetailSection/>
            <Footer/>
        </div>
    );
};

export default StockDetailPage;