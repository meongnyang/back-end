package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meong.nyang.dto.WalkIndexResponseDto;
import meong.nyang.exception.CustomException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

import static meong.nyang.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalkService {
    public WalkIndexResponseDto walkIndex(String nx, String ny, String category, String sidoName, String district) throws IOException, ParseException {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("중구", 0);
        map.put("종로구", 2);
        map.put("용산구", 5);
        map.put("광진구", 6);
        map.put("성동구", 7);
        map.put("중랑구", 9);
        map.put("동대문구", 10);
        map.put("성북구", 12);
        map.put("도봉구", 14);
        map.put("은평구", 15);
        map.put("서대문구", 16);
        map.put("마포구", 17);
        map.put("강서구", 19);
        map.put("구로구", 21);
        map.put("영등포구", 22);
        map.put("동작구", 24);
        map.put("관악구", 26);
        map.put("강남구", 27);
        map.put("서초구", 28);
        map.put("송파구", 31);
        map.put("강동구", 32);
        map.put("금천구", 34);
        map.put("강북구", 36);
        map.put("양천구", 37);
        map.put("노원구", 38);
        HashMap<Integer, String> indexmap = new HashMap<>();
        indexmap.put(1, "아주 좋음");
        indexmap.put(2, "좋음");
        indexmap.put(3, "보통");
        indexmap.put(4, "나쁨");
        indexmap.put(5, "아주 나쁨");
        HashMap<String, String> walkmap = new HashMap<>();
        walkmap.put("아주 좋음", "아주 재밌게 산책하세요!");
        walkmap.put("좋음", "재밌게 산책하세요!");
        walkmap.put("보통", "견종에 따라 조심할 필요가 있습니다!");
        walkmap.put("나쁨", "산책하기에 좋은 날씨는 아닙니다!");
        walkmap.put("아주 나쁨", "반려견의 건강을 위해 장시간 산책은 피하세요!");

        int walkIndex = 0;
        String[] weather = weatherInfo(nx, ny);
        int temperature = Integer.parseInt(weather[0]);
        int rainy = Integer.parseInt(weather[1]);
        if (temperature <= -12)
            walkIndex = 5;
        else if (temperature <= -9) {
            if (Objects.equals(category, "중형견") | Objects.equals(category, "대형견")) walkIndex = 4;
            else walkIndex = 5;
        } else if (temperature <= -6) {
            if (Objects.equals(category, "소형견")) walkIndex = 5;
            else if (Objects.equals(category, "중형견")) walkIndex = 4;
            else walkIndex = 3;
        } else if (temperature <= -4) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 4;
            else walkIndex = 3;
        } else if (temperature <= 1)
            walkIndex = 3;
        else if (temperature <= 4) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 3;
            else walkIndex = 2;
        } else if (temperature <= 7) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 2;
            else walkIndex = 1;
        } else if (temperature <= 10) {
            if (Objects.equals(category, "소형견")) walkIndex = 2;
            else walkIndex = 1;
        } else if (temperature <= 15) {
            walkIndex = 1;
        } else if (temperature <= 18) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 1;
            else walkIndex = 2;
        } else if (temperature <= 21) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 2;
            else walkIndex = 3;
        } else if (temperature <= 23) {
            walkIndex = 3;
        } else if (temperature <= 26) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 3;
            else walkIndex = 4;
        } else if (temperature <= 29) {
            if (Objects.equals(category, "소형견") | Objects.equals(category, "중형견")) walkIndex = 4;
            else walkIndex = 5;
        } else
            walkIndex = 5;
        if (rainy != 0) walkIndex += 2;

        String[] dust = findDust(sidoName, map.get(district));
        int pm10 = Integer.parseInt(dust[0]);
        int pm25 = Integer.parseInt(dust[1]);
        double o3 = Double.parseDouble(dust[2]);

        if (pm10 >= 51) walkIndex += 2;
        if (pm25 >= 26) walkIndex += 3;
        if (o3 >= 0.09) walkIndex += 2;

        if (walkIndex > 5) walkIndex = 5;

        return new WalkIndexResponseDto(indexmap.get(walkIndex), walkmap.get(indexmap.get(walkIndex)), temperature, o3, pm10, pm25);
    }
    public String[] weatherInfo(String nx, String ny) throws IOException, ParseException {
        String apiUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String serviceKey = "6d0gcj819%2FE2tUb8OWd5yJZVaneyb%2B%2FeTDjbY74rAdWmyYzAEcwss0HHDEaHoAaeqYRbEHJDTMBxcVQ0pk6ctQ%3D%3D";
        String type = "JSON";
        int pageNo = 1;
        int numOfRows = 100;
        String baseDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int baseTime = LocalTime.now().getHour();
        if (baseTime < 2) {
            baseDate = LocalDateTime.now().plusHours(-3).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (baseTime == 0) baseTime = LocalTime.now().plusHours(-1).getHour();
            else baseTime = LocalTime.now().plusHours(-2).getHour();
        }
        if (baseTime % 3 == 0) baseTime -= 1;
        else if (baseTime % 3 == 1) baseTime -= 2;
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(serviceKey); //서비스키
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8)); /*페이지번호*/
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("dataType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(type, StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("base_date", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(baseDate, StandardCharsets.UTF_8)); /* 조회하고싶은 날짜*/
        urlBuilder.append("&").append(URLEncoder.encode("base_time", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(baseTime)+"00", StandardCharsets.UTF_8));
        urlBuilder.append("&").append(URLEncoder.encode("nx", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(nx, StandardCharsets.UTF_8)); //경도
        urlBuilder.append("&").append(URLEncoder.encode("ny", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(ny, StandardCharsets.UTF_8)); //위도

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());
        JSONObject response = (JSONObject) object.get("response");
        JSONObject header = (JSONObject) response.get("header");
        String resultCode = (String) header.get("resultCode");
        if (!resultCode.equals("00")) {
            throw new CustomException(NOT_FOUND_WEATHER);
        }
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");
        JSONObject tmp = (JSONObject) item.get(0);
        JSONObject pty = (JSONObject) item.get(6);
        String temperature = (String) tmp.get("fcstValue");
        String weather = (String) pty.get("fcstValue");
        return new String[] {temperature, weather};
    }

    public String[] findDust(String sidoName, Integer districtidx) throws IOException, ParseException {
        String apiUrl = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
        String serviceKey = "6d0gcj819%2FE2tUb8OWd5yJZVaneyb%2B%2FeTDjbY74rAdWmyYzAEcwss0HHDEaHoAaeqYRbEHJDTMBxcVQ0pk6ctQ%3D%3D";
        String returnType = "json";
        double ver = 1.3;
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(serviceKey); //서비스키
        urlBuilder.append("&").append(URLEncoder.encode("returnType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(returnType, StandardCharsets.UTF_8)); //반환 타입
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("100", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); //페이지 번호
        urlBuilder.append("&").append(URLEncoder.encode("sidoName", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(sidoName, StandardCharsets.UTF_8)); //시 이름
        urlBuilder.append("&").append(URLEncoder.encode("ver", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(String.valueOf(ver), StandardCharsets.UTF_8)); //버전 정보

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());
        JSONObject response = (JSONObject) object.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONArray items = (JSONArray) body.get("items");
        if (items.isEmpty()) {
            throw new CustomException(NOT_FOUND_FINEDUST);
        }
        JSONObject weatherInfo = (JSONObject) items.get(districtidx);
        String pm10 = (String) weatherInfo.get("pm10Value");
        String pm25 = (String) weatherInfo.get("pm25Value");
        String o3 = (String) weatherInfo.get("o3Value");
        return new String[] {pm10, pm25, o3};
    }

}
