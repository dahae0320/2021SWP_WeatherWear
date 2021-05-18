//package com.example.a2021swp_weatherwear;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.security.cert.X509Certificate;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//
//import javax.net.ssl.HostnameVerifier;
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLSession;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
//class PVWeather{
//
//    /***********************************************************
//     * 어제시간 가져오기
//     * @return
//     * *********************************************************/
//    public String fn_Yesterday()
//    {
//        SimpleDateFormat Format = new SimpleDateFormat("yyyyMMdd HH");
//        Calendar cal = Calendar.getInstance();
//
//        cal.add(Calendar.DATE,-1);
//        String timedate = Format.format(cal.getTime());
//
//        return timedate;
//    }
//
//    /*******************************************************************************
//     * 필요데이터: 어제날짜 및 시간, 서비스키
//     * stnIds = 기상 관측소 코드 (기상관측위치에 따라 데이터 가져옴)
//     * 기상청 API 정책 상 어제데이터를 가져옴
//     * @param startDt
//     * @param startHh
//     * @param endDt
//     * @param endHh
//     * @return
//     * ******************************************************************************/
//    public String fn_PVConnect(String startDt, String startHh, String endDt, String endHh)
//    {
//        BufferedReader br;
//        String Connect = "";
//        String apikey = "서비스키";
//
//        String add = "https://data.kma.go.kr/apiData/getData?type=json&dataCd=ASOS&dateCd=HR"
//                + "&startDt=" + startDt + "&startHh=" + startHh + "&endDt=" + endDt + "&endHh=" + endHh + "&stnIds=108&schListCnt=10&pageIndex=1&apiKey=" + apikey ;
//
//        try {
//
//            URL url = new URL(add);
//            HttpsConnect();
//
//            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("Content-type", "application/json");
//            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
//            {
//                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//            }
//            else
//            {
//                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//            }
//
//            String ResData = br.readLine();
//
//            if(ResData == null)
//            {
//                System.out.println("응답데이터 == NULL");
//            }
//            else
//            {
//
//                Connect = fn_Json(ResData);
//
//            }
//            br.close();
//            conn.disconnect();
//        }catch(Exception e)
//        {
//            System.out.println(e.getMessage());
//        }
//
//        return Connect;
//    }
//
//    /****************************************************************************
//     * JSON 데이터 파싱함수
//     * 데이터 추출
//     * @param Data
//     * **************************************************************************/
//    public String fn_Json(String Data)
//    {
//
//        JSONArray PVWeatherData;
//        JSONObject infoList;
//        String info = "";
//        String date = "";
//        String icsr = ""; //일사량
//        String ij = "";   //일조량
//        String Value = "";
//        String ts = ""; //지면온도
//        String stn_id = ""; //지점번호
//        String stn_nm = "";
//        try {
//
//            JSONParser parsar = new JSONParser();
//
//            JSONArray obj = (JSONArray) parsar.parse(Data);
//            JSONObject AData = (JSONObject) obj.get(3);
//
//            PVWeatherData = (JSONArray) AData.get("info");
//            for(int i = 0; i< PVWeatherData.size(); i++) {
//
//                infoList = (JSONObject) PVWeatherData.get(i);
//
//                date = infoList.get("TM").toString();
//                icsr = infoList.get("ICSR").toString();
//                ij = infoList.get("SS").toString();
//                ts = infoList.get("TS").toString();
//                stn_id = infoList.get("STN_ID").toString();
//                stn_nm = infoList.get("STN_NM").toString();
//
//
//                date  = "날짜: " + date;
//                icsr = "일사량: " + icsr;
//                ij = "일조량: " + ij;
//                ts = "지면온도: " + ts;
//                stn_nm = "지역 명: " + stn_nm;
//                stn_id = "지역 코드: " + stn_id;
//                Value += date + ","  + icsr + "," + ij +  "," + ts + "," + stn_nm  + "," +stn_id  + ",";
//            }
//
//
//        }catch(Exception e) {
//            e.getMessage();
//        }
//
//        return Value;
//    }
//
//    /****************************************************************************
//     * HTTP -> HTTPS 연결 도와주는 함수(리다이렉트)
//     * 302 REDIRECT 에러 해결
//     * **************************************************************************/
//    public void HttpsConnect()
//    {
//        try {
//            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
//                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                    return null; }
//
//                public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                }
//
//                public void checkServerTrusted(X509Certificate[] certs, String authType) {
//
//                }
//            } };
//
//            SSLContext sc = SSLContext.getInstance("SSL");
//            sc.init(null, trustAllCerts, new java.security.SecureRandom());
//            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//
//            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
//                public boolean verify(String string,SSLSession ssls) {
//                    return true;
//                }
//            });
//        }catch(Exception e)
//        {
//            System.out.println(e);
//        }
//
//    }
//
//}
//
//
//public class WeatherAPI {
//
//    public static void main(String[] args) throws IOException{
//
//        PVWeather pv = new PVWeather();
//        String PVresponse = "";
//        String[] PVArray ;
//        String yes = pv.fn_Yesterday();
//        String Yester = yes.substring(0, 8);
//        String HH = yes.substring(9,11);
//        String TT = "09";
//
//        PVresponse = pv.fn_PVConnect(Yester, TT, Yester, HH);
//        PVArray = PVresponse.split(",");
//        for(int i =0; i< PVArray.length; i++) {
//            System.out.println(PVArray[i]);
//        }
//
//
//    }
//}