package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Efficacy;

@Getter
public class EfficacyResponseDto {
    private Long efficacyId;
    private String name;

    public EfficacyResponseDto(Efficacy efficacy) {
        this.efficacyId = efficacy.getId();
        this.name = efficacy.getName();
    }
}