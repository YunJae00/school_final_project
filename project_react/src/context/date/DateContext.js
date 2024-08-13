import { createContext, useContext, useEffect, useState } from "react";

export const DateContext = createContext();
export const useDate = () => useContext(DateContext);

const DateProvider = ({ children }) => {
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    useEffect(() => {
        const today = new Date();
        const firstDayOfWeek = new Date(today);
        firstDayOfWeek.setDate(today.getDate() - today.getDay() + 1); // 월요일로 설정
        const lastDayOfWeek = new Date(firstDayOfWeek);
        lastDayOfWeek.setDate(firstDayOfWeek.getDate() + 6); // 일요일로 설정

        setStartDate(firstDayOfWeek.toISOString().split('T')[0]);
        setEndDate(lastDayOfWeek.toISOString().split('T')[0]);
    }, []);

    return (
        <DateContext.Provider value={{ startDate, endDate }}>
            {children}
        </DateContext.Provider>
    );
};

export default DateProvider;
