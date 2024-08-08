import React, { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import Header from "../Header";
import Footer from "../Footer";
import StockDetailSection from "./section/StockDetailSection";
import StockDetailAnalyzeSection from "./section/StockDetailAnalyzeSection";
import { fetchTestResultByStockId, fetchPredictionResultByStockId } from "../../../../api/ApiService";

const StockDetailPage = () => {
    const { state } = useLocation();
    const stock = state?.stock;

    const [testResult, setTestResult] = useState(null);
    const [predictionResult, setPredictionResult] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const testResultResponse = await fetchTestResultByStockId(stock.isinCd);
                console.log("Test Result Data:", testResultResponse.data);
                setTestResult(testResultResponse.data);

                const predictionResultResponse = await fetchPredictionResultByStockId(stock.isinCd);
                console.log("Prediction Result Data:", predictionResultResponse.data);
                setPredictionResult(predictionResultResponse.data);
            } catch (error) {
                console.error("Error fetching data:", error);
            }
        };

        fetchData();
    }, [stock.isinCd]);

    return (
        <div>
            <Header />
            <StockDetailSection stock={stock} />
            <StockDetailAnalyzeSection
                testResult={testResult}
                predictionResult={predictionResult}
            />
            <Footer />
        </div>
    );
};

export default StockDetailPage;
