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

    @Builder
    public WalkIndexResponseDto(String index, String explanation, int temperature, double o3, int pm10, int pm25) {
        this.index = index;
        this.explanation = explanation;
        this.temperature = temperature;
        this.o3 = o3;
        this.pm10 = pm10;
        this.pm25 = pm25;
    }
}
