import React, { useState, useEffect } from 'react';
import styled, { css } from 'styled-components';
import ApexChart from 'react-apexcharts';
import { executeGetDailyStockData } from '../../../../api/ApiService';

const Container = styled.div`
    display: flex;
    justify-content: space-between;
    height: 400px;
    width: 100%;
    gap: 30px;
`;

const InnerContainer = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: start;
    width: 786px;
    height: 100%;
`;

const Title = styled.p`
    font-weight: bold;
    font-size: 17px;
    margin: 10px 0 0 10px;
`;

const ChartWrapper = styled.div`
    height: 100%;
`;

const ButtonContainer = styled.div`
    display: flex;
    flex-direction: column;
    width: 378px;
    gap: 20px;
`;

const StockButtonWrapper = styled.div`
    display: flex;
    gap: 30px;
    width: 100%;
`;

const StockButton = styled.button`
    padding: 10px;
    width: 100%;
    height: 80px;
    background-color: #fff;
    border: 1px solid #000;
    cursor: pointer;
    ${props => props.active && css`
        background-color: #d3d3d3;
        border-color: #000;
    `}
`;

const StockChartContainer = () => {
    const [data, setData] = useState([]);
    const [selectedIsinCd, setSelectedIsinCd] = useState('HK0000057197'); // 기본 주식 코드 설정

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
            mode: 'light',
        },
        chart: {
            height: '100%',
            width: '100%',
            toolbar: {
                tools: {},
            },
        },
        grid: {
            show: true,
            borderColor: '#e0e0e0', // 그리드 색상 변경
        },
        plotOptions: {
            candlestick: {
                colors: {
                    upward: '#EF403C',
                    downward: '#004FFE'
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
            categories: data.map(date => date.date),
            axisBorder: {
                show: false,
            },
            axisTicks: {
                show: false,
            },
        },
        yaxis: {
            show: true,
            tooltip: {
                enabled: true
            },
        },
        tooltip: {
            y: {
                formatter: v => `$ ${v.toFixed(2)}`,
            },
            x: {
                formatter: (value, { series, seriesIndex, dataPointIndex, w }) => {
                    const date = new Date(value);
                    return `${date.getMonth() + 1}.${date.getDate()}`;
                }
            }
        },
    };

    return (
        <Container>
            <InnerContainer>
                <Title>인기 주식 TOP4 시장 요약 > </Title>
                <ChartWrapper>
                    {selectedIsinCd !== null && (
                        <ApexChart type="candlestick" series={series} options={options} height="100%" width="100%"/>
                    )}
                </ChartWrapper>
            </InnerContainer>
            <ButtonContainer>
                <Title>인기 주식 TOP4 자세히 보기 > </Title>
                <StockButtonWrapper>
                    <StockButton active={selectedIsinCd === 'HK0000057197'} onClick={() => setSelectedIsinCd('HK0000057197')}>Stock 1</StockButton>
                    <StockButton active={selectedIsinCd === 'HK0000214814'} onClick={() => setSelectedIsinCd('HK0000214814')}>Stock 2</StockButton>
                </StockButtonWrapper>
                <StockButtonWrapper>
                    <StockButton active={selectedIsinCd === 'HK0000295359'} onClick={() => setSelectedIsinCd('HK0000295359')}>Stock 3</StockButton>
                    <StockButton active={selectedIsinCd === 'HK0000307485'} onClick={() => setSelectedIsinCd('HK0000307485')}>Stock 4</StockButton>
                </StockButtonWrapper>
            </ButtonContainer>
        </Container>
    );
}

export default StockChartContainer;
