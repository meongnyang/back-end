package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class WalkIndexResponseDto {
    String index;
    String explanation;
    int temperature;
    double o3;
    int pm10;
    int pm25;
    String o3exp;
    String pm10exp;
    String pm25exp;
    String weather;

    @Builder
    public WalkIndexResponseDto(String index, String explanation, int temperature, double o3, int pm10, int pm25, String o3exp, String pm10exp, String pm25exp, String weather) {
        this.index = index;
        this.explanation = explanation;
        this.temperature = temperature;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
        this.o3exp = o3exp;
        this.pm10exp = pm10exp;
        this.pm25exp = pm25exp;
        this.weather = weather;
    }
}
