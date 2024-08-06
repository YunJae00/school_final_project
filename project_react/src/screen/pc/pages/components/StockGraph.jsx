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
                show: true, // 툴바를 표시하여 resetZoom 버튼을 추가
                tools: {
                    download: false, // 다운로드 버튼을 제거합니다.
                    reset: true, // resetZoom 버튼을 활성화합니다.
                    zoom: false, // 줌 비활성화
                    pan: true, // 드래그 이동 활성화
                    zoomin: true,
                    zoomout: true,
                    selection: false, // 선택 비활성화
                },
                autoSelected: 'pan' // 기본 도구를 pan으로 설정합니다.
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
