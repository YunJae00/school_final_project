import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import ComputerMainPage from "./ComputerMainPage";
import ComputerLoginPage from "./ComputerLoginPage";
import AuthProvider from "./security/AuthContext";

const ComputerPages = () => {
    return(
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path='/main' element={<ComputerMainPage/>}></Route>
                    <Route path='/login' element={<ComputerLoginPage/>}></Route>
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default ComputerPages;