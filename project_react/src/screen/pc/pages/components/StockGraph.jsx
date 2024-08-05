import React from 'react';
import ApexChart from 'react-apexcharts';

const StockGraph = ({ stockData }) => {
    const series = [
        {
            data: stockData.map(price => ({
                x: new Date(price.basDt),
                y: [price.mkp, price.hipr, price.lopr, price.clpr],
            })),
        },
    ];

    const options = {
        chart: {
            type: 'candlestick',
            height: '100%',
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
            type: 'datetime',
            labels: {
                format: 'MM.dd',
            },
            axisBorder: {
                show: false,
            },
            axisTicks: {
                show: false,
            },
        },
        yaxis: {
            tooltip: {
                enabled: true,
            },
        },
        tooltip: {
            enabled: true,
            x: {
                format: 'yyyy-MM-dd',
            },
        },
    };

    return <ApexChart type="candlestick" series={series} options={options} height="100%" width="100%" />;
};

export default StockGraph;
