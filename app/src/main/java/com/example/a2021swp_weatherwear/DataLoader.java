package com.example.a2021swp_weatherwear;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataLoader {
    public String DateLoader(int x){
        Date date = new Date();
        SimpleDateFormat sformat = new SimpleDateFormat("yyyyMMdd HHmm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.HOUR, x);
        String now = sformat.format(calendar.getTime());

        return now;
    }
}
