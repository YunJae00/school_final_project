import React from "react";
import Header from "../Header";
import Footer from "../Footer";
import StockDetailSection from "./section/StockDetailSection";
import StockDetailAnalyzeSection from "./section/StockDetailAnalyzeSection";

const StockDetailPage = () => {
    return(
        <div>
            <Header/>
            <StockDetailSection/>
            <StockDetailAnalyzeSection/>
            <Footer/>
        </div>
    );
};

export default StockDetailPage;