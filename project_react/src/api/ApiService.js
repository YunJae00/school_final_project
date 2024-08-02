import { apiClient } from "./ApiClient";

export const executeLoginService = (email, password) =>
    apiClient.post(`/api/auth/login`, { email, password });

export const executeRegisterMemberService = (user) =>
    apiClient.post(`/api/auth/signup`, user);

export const executeCheckEmailService = (email) =>
    apiClient.get(`/api/members/${email}`);

export const executeGetLatestIndexData = () =>
    apiClient.get(`/api/index/latest-multiple`);

export const executeGetDailyStockData = (isinCd, startDate, endDate) =>
    apiClient.get(`/api/v1/stock/public/daily-data`, {
        params: {
            isinCd,
            startDate,
            endDate
        }
    });

//admin용
export const executeSearchStocks = (query) =>
    apiClient.get(`/api/v1/stock/search`, { params: { query } });

export const executeGetWeeklyStocks = (startDate) =>
    apiClient.get(`/api/v1/stock/weekly`, { params: { startDate } });

export const executeAddStockToWeekly = (startDate, stockId) =>
    apiClient.post(`/api/v1/stock/weekly/${stockId}`, null, { params: { startDate } });

export const executeRemoveStockFromWeekly = (startDate, stockId) =>
    apiClient.delete(`/api/v1/stock/weekly/${stockId}`, { params: { startDate } });