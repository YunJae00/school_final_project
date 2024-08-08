import { apiClient } from "./ApiClient";

export const executeLoginService = (email, password) =>
    apiClient.post(`/api/auth/login`, { email, password });

export const executeRegisterMemberService = (user) =>
    apiClient.post(`/api/auth/signup`, user);

export const executeCheckEmailService = (email) =>
    apiClient.get(`/api/members/${email}`);

export const executeGetLatestIndexData = () =>
    apiClient.get(`/api/index/latest-multiple`);


export const executeGetWeeklyStocksForList = () =>
    apiClient.get(`/api/v1/stock/weekly/latest`);

// analyze
export const fetchTestResultByStockId = (stockId) =>
    apiClient.get(`/api/v1/test-result/${stockId}`);

export const fetchPredictionResultByStockId = (stockId) =>
    apiClient.get(`/api/v1/prediction-result/${stockId}`);

//admin용
export const executeSearchStocks = (query) =>
    apiClient.get(`/api/v1/stock/search`, { params: { query } });

export const executeGetWeeklyStocks = (startDate) =>
    apiClient.get(`/api/v1/stock/weekly`, { params: { startDate } });

export const executeAddStockToWeekly = (startDate, stockId) =>
    apiClient.post(`/api/v1/stock/weekly/${stockId}`, null, { params: { startDate } });

export const executeRemoveStockFromWeekly = (startDate, stockId) =>
    apiClient.delete(`/api/v1/stock/weekly/${stockId}`, { params: { startDate } });

export const executeFetchMultipleStockData = (stockRequests) =>
    apiClient.post(`/api/v1/stock/fetch-multiple-stocks`, stockRequests);