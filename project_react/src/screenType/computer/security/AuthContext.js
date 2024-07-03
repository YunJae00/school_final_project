import { createContext, useContext, useEffect, useState } from "react";
import { executeLoginService } from "../../../api/AuthenticationApiService";
import { apiClient } from "../../../api/ApiClient";

export const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

const AuthProvider = ({ children }) => {
    const [isAuthenticated, setAuthenticated] = useState(false);
    const [memberEmail, setmemberEmail] = useState(null);
    const [token, setToken] = useState(null);

    useEffect(() => {
        const storedToken = localStorage.getItem("token");
        const storedMemberEmail = localStorage.getItem("memberEmail");
        if (storedToken && storedMemberEmail) {
            setToken(storedToken);
            setmemberEmail(storedMemberEmail);
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

            if (response.status === 200) {
                const jwtToken = 'Bearer ' + response.data.accessToken;
                setmemberEmail(memberEmail);
                setAuthenticated(true);
                setToken(jwtToken);
                localStorage.setItem("isLoggedIn", "1");
                localStorage.setItem("token", jwtToken);
                localStorage.setItem("memberEmail", memberEmail);
                apiClient.interceptors.request.use((config) => {
                    console.log('intercepting and adding a token');
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
        setmemberEmail(null);
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
}

export default AuthProvider;