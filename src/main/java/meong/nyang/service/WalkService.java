package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meong.nyang.domain.Station;
import meong.nyang.dto.WalkIndexResponseDto;
import meong.nyang.exception.CustomException;
import meong.nyang.repository.StationRepository;
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
    private final StationRepository stationRepository;
    public WalkIndexResponseDto walkIndex(Double longitude, Double latitude, String category) throws IOException, ParseException {
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
        String[] weather = weatherInfo(Math.round(latitude), Math.round(longitude));
        int temperature = Integer.parseInt(weather[0]);
        int sky = Integer.parseInt(weather[1]);
        int rainy = Integer.parseInt(weather[2]);
        if (temperature <= -12)
            walkIndex = 5;
        else if (temperature <= -9) {
            if (Objects.equals(category, "중형견") | Objects.equals(category, "대형견")) walkIndex = 4;
            else walkIndex = 5;
        } else if (temperature <= -6) {
            if (Objects.equals(category, "소형견")) walkIndex = 5;
            else if (Objects.equals(category, "중형견")) walkIndex = 4;
            else walkIndex = 3;
        } else {
            boolean size = Objects.equals(category, "소형견") | Objects.equals(category, "중형견");
            if (temperature <= -4) {
                if (size) walkIndex = 4;
                else walkIndex = 3;
            } else if (temperature <= 1)
                walkIndex = 3;
            else if (temperature <= 4) {
                if (size) walkIndex = 3;
                else walkIndex = 2;
            } else if (temperature <= 7) {
                if (size) walkIndex = 2;
                else walkIndex = 1;
            } else if (temperature <= 10) {
                if (Objects.equals(category, "소형견")) walkIndex = 2;
                else walkIndex = 1;
            } else if (temperature <= 15) {
                walkIndex = 1;
            } else if (temperature <= 18) {
                if (size) walkIndex = 1;
                else walkIndex = 2;
            } else if (temperature <= 21) {
                if (size) walkIndex = 2;
                else walkIndex = 3;
            } else if (temperature <= 23) {
                walkIndex = 3;
            } else if (temperature <= 26) {
                if (size) walkIndex = 3;
                else walkIndex = 4;
            } else if (temperature <= 29) {
                if (size) walkIndex = 4;
                else walkIndex = 5;
            } else
                walkIndex = 5;
        }
        if (rainy != 0) walkIndex += 2;
        HashMap<Integer, String> weatherMap = new HashMap<Integer, String>();
        weatherMap.put(1, "비");
        weatherMap.put(3, "눈");
        weatherMap.put(4, "소나기");
        HashMap<Integer, String> skyMap = new HashMap<Integer, String>();
        skyMap.put(1, "맑음");
        skyMap.put(3, "구름많음");
        skyMap.put(4, "흐림");
        String weatherInfo;
        if (rainy != 0) weatherInfo = weatherMap.get(rainy);
        else weatherInfo = skyMap.get(sky);

        String[] dust = findDust(longitude, latitude);
        int pm10 = Integer.parseInt(dust[0]);
        int pm25 = Integer.parseInt(dust[1]);
        double o3 = Double.parseDouble(dust[2]);
        if (pm10 >= 51) walkIndex += 2;
        if (pm25 >= 26) walkIndex += 3;
        if (o3 >= 0.09) walkIndex += 2;
        String pm10exp;
        String pm25exp;
        String o3exp;
        if (pm10 <= 15) pm10exp = "매우좋음";
        else if (pm10 <= 30) pm10exp = "좋음";
        else if (pm10 <= 40) pm10exp = "양호";
        else if (pm10 <= 50) pm10exp = "보통";
        else if (pm10 <= 75) pm10exp = "나쁨";
        else if (pm10 <= 100) pm10exp = "상당히 나쁨";
        else if (pm10 <= 150) pm10exp = "매우 나쁨";
        else pm10exp = "최악";
        if (pm25 <= 8) pm25exp = "매우좋음";
        else if (pm25 <= 15) pm25exp = "좋음";
        else if (pm25 <= 20) pm25exp = "양호";
        else if (pm25 <= 25) pm25exp = "보통";
        else if (pm25 <= 37) pm25exp = "나쁨";
        else if (pm25 <= 50) pm25exp = "상당히 나쁨";
        else if (pm25 <= 75) pm25exp = "매우 나쁨";
        else pm25exp = "최악";
        if (o3 <= 0.02) o3exp = "매우좋음";
        else if (o3 <= 0.03) o3exp = "좋음";
        else if (o3 <= 0.06) o3exp = "양호";
        else if (o3 <= 0.09) o3exp = "보통";
        else if (o3 <= 0.12) o3exp = "나쁨";
        else if (o3 <= 0.15) o3exp = "상당히 나쁨";
        else if (o3 <= 0.38) o3exp = "매우 나쁨";
        else o3exp = "최악";

        if (walkIndex > 5) walkIndex = 5;
        return new WalkIndexResponseDto(indexmap.get(walkIndex), walkmap.get(indexmap.get(walkIndex)), temperature, o3, pm10, pm25, o3exp, pm10exp, pm25exp, weatherInfo);
    }
    public String[] weatherInfo(Long nx, Long ny) throws IOException, ParseException {
        String apiUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        String serviceKey = "6d0gcj819%2FE2tUb8OWd5yJZVaneyb%2B%2FeTDjbY74rAdWmyYzAEcwss0HHDEaHoAaeqYRbEHJDTMBxcVQ0pk6ctQ%3D%3D";
        String type = "JSON";
        int pageNo = 1;
        int numOfRows = 100;
        String baseDate = LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int baseTime = LocalTime.now().plusHours(9).getHour();
        if (baseTime < 2) {
            baseDate = LocalDateTime.now().plusHours(6).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            if (baseTime == 0) baseTime = LocalTime.now().plusHours(8).getHour();
            else baseTime = LocalTime.now().plusHours(7).getHour();
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
        urlBuilder.append("&").append(URLEncoder.encode("nx", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(nx.toString(), StandardCharsets.UTF_8)); //경도
        urlBuilder.append("&").append(URLEncoder.encode("ny", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(ny.toString(), StandardCharsets.UTF_8)); //위도

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
        JSONObject cloud = (JSONObject) item.get(5);
        JSONObject pty = (JSONObject) item.get(6);
        String temperature = (String) tmp.get("fcstValue");
        String sky = (String) cloud.get("fcstValue");
        String weather = (String) pty.get("fcstValue");
        return new String[] {temperature, sky, weather};
    }

    public String[] findDust(Double longitude, Double latitude) throws IOException, ParseException {
        String station = stationRepository.findStationByLocation(longitude, latitude);
        String apiUrl = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty";
        String serviceKey = "6d0gcj819%2FE2tUb8OWd5yJZVaneyb%2B%2FeTDjbY74rAdWmyYzAEcwss0HHDEaHoAaeqYRbEHJDTMBxcVQ0pk6ctQ%3D%3D";
        String returnType = "json";
        double ver = 1.3;
        StringBuilder urlBuilder = new StringBuilder(apiUrl);
        urlBuilder.append("?").append(URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)).append("=").append(serviceKey); //서비스키
        urlBuilder.append("&").append(URLEncoder.encode("returnType", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(returnType, StandardCharsets.UTF_8)); //반환 타입
        urlBuilder.append("&").append(URLEncoder.encode("numOfRows", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); /*한 페이지 결과 수*/
        urlBuilder.append("&").append(URLEncoder.encode("pageNo", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("1", StandardCharsets.UTF_8)); //페이지 번호
        urlBuilder.append("&").append(URLEncoder.encode("stationName", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(station, StandardCharsets.UTF_8)); //시 이름
        urlBuilder.append("&").append(URLEncoder.encode("dataTerm", StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode("DAILY", StandardCharsets.UTF_8)); //시 이름
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
        JSONObject weatherInfo = (JSONObject) items.get(0);
        String pm10 = (String) weatherInfo.get("pm10Value");
        String pm25 = (String) weatherInfo.get("pm25Value");
        String o3 = (String) weatherInfo.get("o3Value");
        return new String[] {pm10, pm25, o3};
    }

}
