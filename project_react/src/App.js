import { useMediaQuery } from "react-responsive"
import AuthProvider from "./security/AuthContext";
import PcScreen from "./screen/pc/PcScreen";
import TabletScreen from "./screen/tablet/TabletScreen";
import MobileScreen from "./screen/mobile/MobileScreen";

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
        <AuthProvider>
            {isPc && <PcScreen/>}
            {isTablet && <TabletScreen/>}
            {isMobile && <MobileScreen/>}
        </AuthProvider>
    );
}

export default App;
