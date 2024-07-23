import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/main/MainPage";
import StockListPage from "./pages/stockList/StockListPage";
import StockDetailPage from "./pages/stockDetail/StockDetailPage";

const PcScreen = () => {
    return(
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<MainPage/>}></Route>
                <Route path='/stock-list' element={<StockListPage/>}></Route>
                <Route path='/stock-detail' element={<StockDetailPage/>}></Route>
            </Routes>
        </BrowserRouter>
    );
};

export default PcScreen;