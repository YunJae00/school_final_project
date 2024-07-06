import React, { useEffect, useState } from "react";
import styled from "styled-components";
import StockIndexComponent from "./components/StockIndexComponent";
import { executeGetLatestIndexData } from "../../../../api/ApiService";

const Container = styled.div`
    display: flex;
    flex-direction: column; 
    height: 100%;
    gap: 10px;
`;

const DateWrapper = styled.div`
    display: flex;
    width: 100%;
    justify-content: center;
    font-size: 15px;
    font-weight: bold;
    color: red;
    opacity: 0.6;
`;

const indexNameMap = {
    "코스피": "KOSPI",
    "코스닥": "KOSDAQ",
    "코스피 200": "KOSPI\n200",
    "코스닥 150": "KOSDAQ\n150",
    "KRX 100": "KRX 100",
    "코스피 50": "KOSPI\n50"
};

const formatDate = (dateString) => {
    if (!dateString) return "";
    const year = dateString.substring(0, 4);
    const month = dateString.substring(4, 6);
    const day = dateString.substring(6, 8);
    return `${year}-${month}-${day}`;
};

const StockIndexContainer = () => {
    const [indexData, setIndexData] = useState([]);
    const [latestDate, setLatestDate] = useState("");

    useEffect(() => {
        executeGetLatestIndexData()
            .then(response => {
                const transformedData = response.data.map(item => ({
                    ...item,
                    indexName: indexNameMap[item.indexName] || item.indexName
                }));

                if (transformedData.length > 0) {
                    setLatestDate(formatDate(transformedData[0].date));
                }

                setIndexData(transformedData);
            })
            .catch(error => console.error("Error fetching data: ", error));
    }, []);

    return (
        <Container>
            <DateWrapper> [* 아래 정보들은 {latestDate} 을 기준으로 합니다 *] </DateWrapper>
            <div style={{ display: 'flex', gap: '30px' }}>
                {indexData.map((data, index) => (
                    <StockIndexComponent
                        key={index}
                        indexText={data.indexName}
                        indexPrice={data.price}
                        indexFluctuation={data.fluctuation}
                    />
                ))}
            </div>
        </Container>
    );
};

export default StockIndexContainer;
