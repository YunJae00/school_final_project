import React, {useEffect, useState} from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import MainPage from "./pages/main/MainPage";
import StockListPage from "./pages/stockList/StockListPage";
import StockDetailPage from "./pages/stockDetail/StockDetailPage";
import AdminPage from "./pages/admin/AdminPage";

const PcScreen = () => {

    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<MainPage />} />
                <Route path='/stock-list' element={<StockListPage />} />
                <Route path='/stock-detail/:stockId' element={<StockDetailPage />} />
                <Route path='/admin' element={<AdminPage />} />
            </Routes>
        </BrowserRouter>
    );
};

export default PcScreen;
