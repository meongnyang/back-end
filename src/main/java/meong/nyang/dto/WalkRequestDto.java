package meong.nyang.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkRequestDto {
    double longitude;
    double latitude;
    String category;
}
