import { createContext, useContext, useEffect, useState } from "react";
import { executeLoginService } from "../api/ApiService";
import { apiClient } from "../api/ApiClient";

export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [memberEmail, setMemberEmail] = useState(null);
    const [token, setToken] = useState(null);

    useEffect(() => {
        const storedToken = localStorage.getItem("token");
        const storedMemberEmail = localStorage.getItem("memberEmail");
        if (storedToken && storedMemberEmail) {
            setToken(storedToken);
            setMemberEmail(storedMemberEmail);
            setAuthenticated(true);
            apiClient.interceptors.request.use((config) => {
                config.headers.Authorization = storedToken;
                return config;
            });
        }
    }, []);

    async function login(memberEmail, password) {
        try {
            const response = await executeLoginService(memberEmail, password);

            if (response.data && response.data.accessToken) {
                const jwtToken = "Bearer " + response.data.accessToken;
                setMemberEmail(memberEmail);
                setAuthenticated(true);
                setToken(jwtToken);
                localStorage.setItem("isLoggedIn", true);
                localStorage.setItem("token", jwtToken);
                localStorage.setItem("memberEmail", memberEmail);
                apiClient.interceptors.request.use((config) => {
                    console.log("intercepting and adding a token");
                    config.headers.Authorization = jwtToken;
                    return config;
                });
                return true;
            } else {
                logout();
                return false;
            }
        } catch (error) {
            logout();
            return false;
        }
    }

    function logout() {
        setAuthenticated(false);
        setMemberEmail(null);
        setToken(null);
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("token");
        localStorage.removeItem("memberEmail");
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, memberEmail, login, logout, token }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;
