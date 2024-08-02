import React, { useState, useEffect } from "react";
import {
    executeSearchStocks,
    executeGetWeeklyStocks,
    executeAddStockToWeekly,
    executeRemoveStockFromWeekly
} from "../../../../api/ApiService";
import Header from "../Header";
import Footer from "../Footer";

const AdminPage = () => {
    const [query, setQuery] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [weeklyStocks, setWeeklyStocks] = useState([]);
    const [startDate, setStartDate] = useState("");

    useEffect(() => {
        const today = new Date();
        const firstDayOfWeek = new Date(today.setDate(today.getDate() - today.getDay()));
        setStartDate(firstDayOfWeek.toISOString().split('T')[0]);

        fetchWeeklyStocks(firstDayOfWeek.toISOString().split('T')[0]);
    }, []);

    const handleSearch = () => {
        executeSearchStocks(query)
            .then((response) => {
                setSearchResults(response.data);
                console.log(response.data)
            })
            .catch((error) => {
                console.error("Error searching stocks:", error);
            });
    };

    const fetchWeeklyStocks = (start) => {
        executeGetWeeklyStocks(start)
            .then((response) => {
                setWeeklyStocks(response.data);
            })
            .catch((error) => {
                console.error("Error fetching weekly stocks:", error);
            });
    };

    const handleAddStock = (stockId) => {
        executeAddStockToWeekly(startDate, stockId)
            .then(() => {
                fetchWeeklyStocks(startDate);
                setQuery(""); // 검색어 초기화
                setSearchResults([]); // 검색 결과 초기화
            })
            .catch((error) => {
                console.error("Error adding stock:", error);
            });
    };

    const handleRemoveStock = (stockId) => {
        executeRemoveStockFromWeekly(startDate, stockId)
            .then(() => {
                fetchWeeklyStocks(startDate);
            })
            .catch((error) => {
                console.error("Error removing stock:", error);
            });
    };

    return (
        <div>
            <Header/>
            <h1>주간 추천 주식 관리</h1>

            <div>
                <h2>주식 검색</h2>
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="주식 이름 검색"
                />
                <button onClick={handleSearch}>검색</button>
                <ul>
                    {searchResults.map((stock) => (
                        <li key={stock.id}>
                            {stock.itmsNm} ({stock.isinCd})
                            <button onClick={() => handleAddStock(stock.id)}>추가</button>
                        </li>
                    ))}
                </ul>
            </div>

            <div>
                <h2>{startDate} 주간 추천 주식</h2>
                <ul>
                    {weeklyStocks.map((stock) => (
                        <li key={stock.id}>
                            {stock.itmsNm} ({stock.isinCd})
                            <button onClick={() => handleRemoveStock(stock.id)}>삭제</button>
                        </li>
                    ))}
                </ul>
            </div>
            <Footer/>
        </div>
    );
};

export default AdminPage;
