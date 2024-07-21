import React from "react";
import styled from "styled-components";

const IndexBoxWrapper = styled.div`

`;

const IndexTitle = styled.div`

`;

const IndexPrice = styled.div`'

`;

const IndexDate = styled.div`'

`;

const IndexBox = ({indexTitle, indexPrice, indexDate}) => {
    return(
        <IndexBoxWrapper>
            <IndexTitle></IndexTitle>
            <IndexPrice></IndexPrice>
            <IndexDate></IndexDate>
        </IndexBoxWrapper>
    );
};

export default IndexBox;