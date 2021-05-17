package com.example.a2021swp_weatherwear;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class apiTest extends Thread {

    public JSONObject s1;
    public JSONObject s2;
    public JSONObject s3;
    public JSONObject s4;


    //public void func(String year, String month, String day) throws IOException, JSONException
    public void func(String year, String month, String day) throws IOException, JSONException
    {
        // 기상청 동네예보 api에서 동네예보조회 요청에 필요한 매개변수들
        String endPoint = "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
        String serviceKey = "sCyFo%2FDKOLW9l1EHk%2BlA0zwanD%2FpAjUbgIhgU5erOQKEcKoh6PUjl%2B%2FQ1WARH4AksQh3sl6FU%2Ff9buPEMK20Rw%3D%3D";
        String pageNo = "1";
        String numOfRows = "10";
        String baseDate = year + month + day; // 원하는 날짜
        String baseTime1 = "1500"; // 원하는 시간
        String baseTime2 = "1700"; // 원하는 시간
        String baseTime3 = "1900"; // 원하는 시간
        String baseTime4 = "2100"; // 원하는 시간
        String nx = "81"; // 위경도임.
        String ny = "75"; // 위경도 정보는 api 문서 볼 것
        // endpoint와 원하는 정보(동네예보조회:getVilageFcst), 서비스키와 기타 요청 매개변수들을 다 합친 쿼리 스트링이다.
        String s1 = endPoint + "/getVilageFcst?serviceKey=" + serviceKey
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "+&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime1
                + "&nx=" + nx
                + "&ny=" + ny;

        String s2 = endPoint + "/getVilageFcst?serviceKey=" + serviceKey
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "+&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime2
                + "&nx=" + nx
                + "&ny=" + ny;

        String s3 = endPoint + "/getVilageFcst?serviceKey=" + serviceKey
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "+&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime3
                + "&nx=" + nx
                + "&ny=" + ny;

        String s4 = endPoint + "/getVilageFcst?serviceKey=" + serviceKey
                + "&pageNo=" + pageNo
                + "&numOfRows=" + numOfRows
                + "+&dataType=JSON"
                + "&base_date=" + baseDate
                + "&base_time=" + baseTime4
                + "&nx=" + nx
                + "&ny=" + ny;

        // Http연결을한다. 호출 Method는 "GET"이다.
        URL url1 = new URL(s1);

        HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
        conn1.setRequestMethod("GET");

        URL url2 = new URL(s2);

        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("GET");

        URL url3 = new URL(s1);

        HttpURLConnection conn3 = (HttpURLConnection) url3.openConnection();
        conn3.setRequestMethod("GET");

        URL url4 = new URL(s4);

        HttpURLConnection conn4 = (HttpURLConnection) url4.openConnection();
        conn4.setRequestMethod("GET");


        // BufferedReader를 이용해서 리턴값을 받고 StringBuilder를 이용해서 라인마다 한줄씩 추가하여 저장한다.


        BufferedReader bufferedReader;
        if(conn1.getResponseCode() >= 200 && conn1.getResponseCode() <= 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn1.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(conn1.getErrorStream()));
        }
        if(conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
        }
        if(conn3.getResponseCode() >= 200 && conn3.getResponseCode() <= 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn3.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(conn3.getErrorStream()));
        }
        if(conn4.getResponseCode() >= 200 && conn4.getResponseCode() <= 300) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn4.getInputStream()));
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(conn4.getErrorStream()));
        }


        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        String result = stringBuilder.toString();
        conn1.disconnect();
        conn2.disconnect();
        conn3.disconnect();
        conn4.disconnect();
        JSONObject mainObject = new JSONObject(result);
        System.out.println(mainObject);
        JSONArray itemArray = mainObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);
            String category = item.getString("category");
            String value = item.getString("fcstValue");
            System.out.println(category + "  " + value);


        }

    }
}
