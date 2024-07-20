import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import MainPage from "./pages/main/MainPage";

const PcScreen = () => {
    return(
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<MainPage/>}></Route>
            </Routes>
        </BrowserRouter>
    );
};

export default PcScreen;