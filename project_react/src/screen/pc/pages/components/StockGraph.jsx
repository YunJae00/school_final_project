import React, { useEffect, useState } from 'react';
import ApexChart from 'react-apexcharts';
import { executeGetDailyStockData } from '../../../../api/ApiService';

const StockGraph = ({ stockCode, startDate, endDate }) => {
    const [data, setData] = useState([]);

    useEffect(() => {
        if (stockCode) {
            executeGetDailyStockData(stockCode, startDate, endDate)
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
