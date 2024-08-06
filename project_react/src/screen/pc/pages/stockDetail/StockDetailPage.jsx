import React from "react";
import { useLocation } from "react-router-dom";
import Header from "../Header";
import Footer from "../Footer";
import StockDetailSection from "./section/StockDetailSection";
import StockDetailAnalyzeSection from "./section/StockDetailAnalyzeSection";

const StockDetailPage = () => {
    const { state } = useLocation(); // useLocation 훅을 사용하여 전달된 데이터 가져오기
    const stock = state?.stock;

    return (
        <div>
            <Header />
            <StockDetailSection stock={stock} />
            <StockDetailAnalyzeSection />
            <Footer />
        </div>
    );
};

export default StockDetailPage;
