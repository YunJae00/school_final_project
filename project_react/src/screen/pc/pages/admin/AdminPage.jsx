import React, { useState, useEffect } from "react";
import {
    executeSearchStocks,
    executeGetWeeklyStocks,
    executeAddStockToWeekly,
    executeRemoveStockFromWeekly,
    executeFetchMultipleStockData, // 이 함수로 교체합니다.
} from "../../../../api/ApiService";
import Header from "../Header";
import Footer from "../Footer";

const AdminPage = () => {
    const [query, setQuery] = useState("");
    const [searchResults, setSearchResults] = useState([]);
    const [weeklyStocks, setWeeklyStocks] = useState([]);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const today = new Date();
        const firstDayOfWeek = new Date(today);
        firstDayOfWeek.setDate(today.getDate() - today.getDay() + 1); // 월요일로 설정
        const lastDayOfWeek = new Date(firstDayOfWeek);
        lastDayOfWeek.setDate(firstDayOfWeek.getDate() + 6); // 일요일로 설정

        setStartDate(firstDayOfWeek.toISOString().split('T')[0]);
        setEndDate(lastDayOfWeek.toISOString().split('T')[0]);

        fetchWeeklyStocks(firstDayOfWeek.toISOString().split('T')[0]);
    }, []);

    const handleSearch = () => {
        setError("");
        setLoading(true);
        executeSearchStocks(query)
            .then((response) => {
                setSearchResults(response.data);
                if (response.data.length === 0) {
                    setError("검색 결과가 없습니다.");
                }
            })
            .catch((error) => {
                console.error("Error searching stocks:", error);
                setError("주식을 검색하는 중 오류가 발생했습니다.");
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const fetchWeeklyStocks = (start) => {
        setError("");
        setLoading(true);
        executeGetWeeklyStocks(start)
            .then((response) => {
                setWeeklyStocks(response.data);
            })
            .catch((error) => {
                console.error("Error fetching weekly stocks:", error);
                setError("주간 추천 주식을 가져오는 중 오류가 발생했습니다.");
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const handleAddStock = (stockId) => {
        setError("");
        setLoading(true);
        executeAddStockToWeekly(startDate, stockId)
            .then(() => {
                fetchWeeklyStocks(startDate);
                setQuery(""); // 검색어 초기화
                setSearchResults([]); // 검색 결과 초기화
            })
            .catch((error) => {
                console.error("Error adding stock:", error);
                setError("주식을 추가하는 중 오류가 발생했습니다.");
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const handleRemoveStock = (stockId) => {
        setError("");
        setLoading(true);
        executeRemoveStockFromWeekly(startDate, stockId)
            .then(() => {
                fetchWeeklyStocks(startDate);
            })
            .catch((error) => {
                console.error("Error removing stock:", error);
                setError("주식을 삭제하는 중 오류가 발생했습니다.");
            })
            .finally(() => {
                setLoading(false);
            });
    };

    const handleSaveWeeklyData = () => {
        if (weeklyStocks.length !== 10) {
            alert("10개의 주식을 선택해야 데이터를 저장할 수 있습니다.");
            return;
        }

        const stockRequests = weeklyStocks.map(stock => ({
            isinCd: stock.isinCd,
            startDate: startDate,
            endDate: endDate
        }));

        console.log(stockRequests);

        setError("");
        setLoading(true);
        executeFetchMultipleStockData(stockRequests)
            .then(() => {
                alert("주식 데이터가 성공적으로 저장되었습니다.");
            })
            .catch((error) => {
                console.error("Error saving weekly stock data:", error);
                setError("주식 데이터를 저장하는 중 오류가 발생했습니다.");
            })
            .finally(() => {
                setLoading(false);
            });
    };

    return (
        <div>
            <Header />
            <h1>주간 추천 주식 관리</h1>

            <div>
                <h2>주식 검색</h2>
                <input
                    type="text"
                    value={query}
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="주식 이름 검색"
                />
                <button onClick={handleSearch} disabled={loading}>
                    {loading ? "검색 중..." : "검색"}
                </button>
                {error && <p style={{ color: "red" }}>{error}</p>}
                <ul>
                    {searchResults.map((stock) => (
                        <li key={stock.id}>
                            {stock.itmsNm} ({stock.isinCd})
                            <button onClick={() => handleAddStock(stock.id)} disabled={loading}>
                                추가
                            </button>
                        </li>
                    ))}
                </ul>
            </div>

            <div>
                <h2>{startDate} ~ {endDate} 주간 추천 주식</h2>
                <ul>
                    {weeklyStocks.map((stock) => (
                        <li key={stock.id}>
                            {stock.itmsNm} ({stock.isinCd})
                            <button onClick={() => handleRemoveStock(stock.id)} disabled={loading}>
                                삭제
                            </button>
                        </li>
                    ))}
                </ul>
                <button onClick={handleSaveWeeklyData} disabled={weeklyStocks.length !== 10 || loading}>
                    {loading ? "저장 중..." : "데이터 저장하기"}
                </button>
            </div>
            <Footer />
        </div>
    );
};

export default AdminPage;
