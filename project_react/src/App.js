import {BrowserRouter, Routes, Route} from "react-router-dom";
import ComputerMainPage from "./screenType/computer/ComputerMainPage";
import PhoneMainPage from "./screenType/phone/PhoneMainPage";
import TabletMainPage from "./screenType/tablet/TabletMainPage";
import { useMediaQuery } from "react-responsive"
import ComputerPages from "./screenType/computer/ComputerPages";

function App() {

    const isPc = useMediaQuery({
        query : "(min-width:1220px)"
    });
    const isTablet = useMediaQuery({
        query : "(min-width:720px) and (max-width:1220px)"
    });
    const isMobile = useMediaQuery({
        query : "(max-width:720px)"
    });

    return (
        <div>
            {isPc && <ComputerPages/>}
            {isTablet && <TabletMainPage/>}
            {isMobile && <PhoneMainPage/>}
        </div>
    );
}

export default App;
