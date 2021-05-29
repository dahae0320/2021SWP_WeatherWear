package com.example.a2021swp_weatherwear;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class WeatherApi {

    public static String getWeatherData(String getDate, String getTime) {

        StringBuffer buffer = new StringBuffer();

        String endPoint = "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
        String serviceKey = "A7FY4Hj7JXPBdELMhYfwdg2RWlyWhiBsx0gogIIYynF%2BblUZfVxDn6gVCQ9uTDUpEPEqfCZ%2B2FW0jXJOYXFo5Q%3D%3D";
        String pageNo = "1";
        String numOfRows = "10";
        String baseDate = getDate; // 원하는 날짜
        String baseTime = getTime;
        String nx = "81"; // 위경도임.
        String ny = "75";

        String queryUrl = endPoint + "getUltraSrtNcst?serviceKey=" + serviceKey
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime
                + "&nx=" + nx
                + "&ny=" + ny;

        try {
            URL url = new URL(queryUrl);

            InputStream is = url.openStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));

            String t;
            int i = 0;
            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        t = xpp.getName();

                        if (t.equals("item")) ;
                        else if (t.equals("obsrValue")) {
                            i++;
                            //기온
                            if (i == 4) {
                                buffer.append("");
                                xpp.next();
                                buffer.append(xpp.getText());
                                System.out.println(xpp.getText());
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        t = xpp.getName(); //태그 이름 얻어오기
                        if (t.equals("item")) ;
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {

        }
        return buffer.toString();
    }
}