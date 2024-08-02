import React, { useEffect, useState } from 'react';
import ApexChart from 'react-apexcharts';
import { executeGetDailyStockData } from '../../../../api/ApiService';

const StockGraph = ({ stockCode, startDate, endDate }) => {
    const [data, setData] = useState([]);

    useEffect(() => {
        // 오늘 날짜 계산
        const today = new Date();
        const todayFormatted = `${today.getFullYear()}${String(today.getMonth() + 1).padStart(2, '0')}${String(today.getDate()).padStart(2, '0')}`;

        // 1년 전 날짜 계산
        const oneYearAgo = new Date(today);
        oneYearAgo.setFullYear(today.getFullYear() - 1);
        const oneYearAgoFormatted = `${oneYearAgo.getFullYear()}${String(oneYearAgo.getMonth() + 1).padStart(2, '0')}${String(oneYearAgo.getDate()).padStart(2, '0')}`;

        // 클라이언트에서 날짜를 보내지 않았을 때 기본값 설정
        const finalStartDate = startDate || oneYearAgoFormatted;
        const finalEndDate = endDate || todayFormatted;

        if (stockCode) {
            executeGetDailyStockData(stockCode, finalStartDate, finalEndDate)
                .then(response => {
                    setData(response.data);
                })
                .catch(error => {
                    console.error('Error fetching stock data:', error);
                });
        }
    }, [stockCode, startDate, endDate]);

    const series = [
        {
            data: data.map(price => ({
                x: new Date(price.basDt),
                y: [price.mkp, price.hipr, price.lopr, price.clpr],
            })),
        },
    ];

    const options = {
        chart: {
            type: 'candlestick',
            height: '100%',
            width: '100%',
            toolbar: {
                show: false,
            },
        },
        grid: {
            show: true,
            borderColor: '#e0e0e0',
        },
        plotOptions: {
            candlestick: {
                colors: {
                    upward: '#EF403C',
                    downward: '#004FFE',
                },
                wick: {
                    useFillColor: true,
                },
            },
        },
        xaxis: {
            labels: {
                show: true,
                format: 'MM.dd',
            },
            type: 'datetime',
            categories: data.map(date => date.basDt),
            axisBorder: {
                show: false,
            },
            axisTicks: {
                show: false,
            },
        },
        yaxis: {
            show: true,
        },
        tooltip: {
            enabled: false,
        },
    };

    return <ApexChart type="candlestick" series={series} options={options} height="100%" width="100%" />;
};

export default StockGraph;
