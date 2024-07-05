import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import ApexChart from 'react-apexcharts';
import { executeGetDailyStockData } from '../../../../api/ApiService';

const Container = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 500px;
    background-color: brown;
    padding: 20px;
    gap: 20px;
`;

const ButtonContainer = styled.div`
    display: flex;
    gap: 10px;
`;

const StockButton = styled.button`
    padding: 10px;
    background-color: #fff;
    border: 1px solid #000;
    cursor: pointer;
`;

const StockChartContainer = () => {
    const [data, setData] = useState([]);
    const [selectedIsinCd, setSelectedIsinCd] = useState(null);

    useEffect(() => {
        if (selectedIsinCd !== null) {
            executeGetDailyStockData(selectedIsinCd)
                .then(response => {
                    setData(response.data);
                })
                .catch(error => {
                    console.error('Error fetching stock data:', error);
                });
        }
    }, [selectedIsinCd]);

    const series = [
        {
            data: data.map(price => ({
                x: new Date(price.date),
                y: [price.open, price.high, price.low, price.close],
            })),
        },
    ];

    const options = {
        theme: {
            mode: 'dark',
        },
        chart: {
            height: 500,
            width: 500,
            toolbar: {
                tools: {},
            },
            background: 'transparent',
        },
        grid: {
            show: false,
        },
        plotOptions: {
            candlestick: {
                wick: {
                    useFillColor: true,
                },
            },
        },
        xaxis: {
            labels: {
                show: false,
                datetimeFormatter: {
                    month: "mmm 'yy",
                },
            },
            type: 'datetime',
            categories: data.map(date => date.date),
            axisBorder: {
                show: false,
            },
            axisTicks: {
                show: false,
            },
        },
        yaxis: {
            show: false,
        },
        tooltip: {
            y: {
                formatter: v => `$ ${v.toFixed(2)}`,
            },
        },
    };

    return (
        <Container>
            <ButtonContainer>
                <StockButton onClick={() => setSelectedIsinCd('HK0000057197')}>Stock 1</StockButton>
                <StockButton onClick={() => setSelectedIsinCd('HK0000214814')}>Stock 2</StockButton>
                <StockButton onClick={() => setSelectedIsinCd('HK0000295359')}>Stock 3</StockButton>
                <StockButton onClick={() => setSelectedIsinCd('HK0000307485')}>Stock 4</StockButton>
            </ButtonContainer>
            {selectedIsinCd !== null && (
                <ApexChart type="candlestick" series={series} options={options} />
            )}
        </Container>
    );
}

export default StockChartContainer;
