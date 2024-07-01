import React from "react";
import styled from "styled-components";

const Container = styled.div`
    display: flex;
    justify-content: center;
    background-color: bisque;
`;

const StockDetailsContainer = styled.div`
    display: flex;
`;

const StockDetail = styled.div`
    display: flex;
    flex-direction: column;
`;

const StockDetailContainer = () => {
    return(
        <Container>
            <div>sd</div>
        </Container>
    );
}

export default StockDetailContainer;