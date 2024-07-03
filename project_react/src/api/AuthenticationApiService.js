import { apiClient } from "./ApiClient";

export const executeLoginService = (email, password) =>
    apiClient.post(`/api/auth/login`, { email, password });

export const executeRegisterMemberService = (user) =>
    apiClient.post(`/api/auth/signup`, user);

export const executeCheckEmailService = (email) =>
    apiClient.get(`/api/members/${email}`);

// export const executeMemberGrouptService = (email) =>
//     apiClient.get(``)
