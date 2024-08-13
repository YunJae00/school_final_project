import { createContext, useContext, useEffect, useState } from "react";
import { executeLoginService } from "../../api/ApiService";
import { apiClient } from "../../api/ApiClient";

export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
    const [isAuthenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        const storedAccessToken = localStorage.getItem("accessToken");
        if (storedAccessToken) {
            setAuthenticated(true);
            apiClient.interceptors.request.use((config) => {
                config.headers.Authorization = storedAccessToken;
                return config;
            });
        }
    }, []);

    async function signIn(memberEmail, password) {
        try {
            const response = await executeLoginService(memberEmail, password);

            if (response.data && response.data.accessToken) {
                const jwtToken = "Bearer " + response.data.accessToken;
                setAuthenticated(true);
                localStorage.setItem("accessToken", jwtToken);
                apiClient.interceptors.request.use((config) => {
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
        localStorage.removeItem("token");
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, signIn, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthProvider;
