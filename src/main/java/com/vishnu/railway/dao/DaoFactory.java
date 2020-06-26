package com.vishnu.railway.dao;

public class DaoFactory {
    private static TimeTableDao timeTableDao = new TimeTableDao();

    public static TimeTableDao getTimeTableDao() {
        return timeTableDao;
    }
}
