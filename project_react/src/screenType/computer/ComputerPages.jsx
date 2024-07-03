import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import ComputerMainPage from "./ComputerMainPage";
import ComputerLoginPage from "./ComputerLoginPage";

const ComputerPages = () => {
    return(
        <BrowserRouter>
            <Routes>
                <Route path='/main' element={<ComputerMainPage/>}></Route>
                <Route path='/login' element={<ComputerLoginPage/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default ComputerPages;