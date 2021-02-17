package com.example.organizze.utils;

import java.text.SimpleDateFormat;

public class DateUtil {
    public static String todayDate(){
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String dateMonthAndYear(String date){
        String modifiedDate[] = date.split("/");
        String day = modifiedDate[0];
        String month = modifiedDate[1];
        String year = modifiedDate[2];

        String monthYear = month + year;
        return monthYear;
    }
}
