package com.javatar.practice.ticketsystem.service;

import com.github.mfathi91.time.PersianDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class DateConvertor {

    public static String toShamsi(String EnterDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(EnterDate);

        // تبدیل به LocalDate
        LocalDate gregorianDate = new java.sql.Date(date.getTime()).toLocalDate();
        // تبدیل تاریخ میلادی به تاریخ شمسی
        PersianDate persianDate = PersianDate.fromGregorian(gregorianDate);
        return persianDate.toString();
    }

    public static String toShamsi_withTime(String EnterDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = formatter.parse(EnterDate);


        long timeInMillis = date.getTime(); // زمان بر حسب میلی‌ثانیه

// استفاده از Calendar برای استخراج ساعت، دقیقه و ثانیه
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        System.out.println("Time: " + hours + ":" + minutes + ":" + seconds);



        // تبدیل به LocalDate
        LocalDate gregorianDate = new java.sql.Date(date.getTime()).toLocalDate();
        // تبدیل تاریخ میلادی به تاریخ شمسی
        PersianDate persianDate = PersianDate.fromGregorian(gregorianDate);

        String result=persianDate.toString()+"-"+ hours + ":" + minutes + ":" + seconds;
        return result;
    }
}
