import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";
import PhoneLoginPage from "./PhoneLoginPage";
import AuthProvider from "../../context/security/AuthContext";
import PhoneMainPage from "./PhoneMainPage";

const PhonePages = () => {
    return(
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path='/main' element={<PhoneMainPage/>}></Route>
                    <Route path='/login' element={<PhoneLoginPage/>}></Route>
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default PhonePages;