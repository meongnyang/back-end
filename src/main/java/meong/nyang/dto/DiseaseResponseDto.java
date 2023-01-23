package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import meong.nyang.domain.Disease;

@Getter
public class DiseaseResponseDto {
    private Long diseaseId;
    private String name;
    private String reason;
    private String manage;

    @Builder
    public DiseaseResponseDto(Disease disease) {
        this.diseaseId = disease.getId();
        this.name = disease.getName();
        this.reason = disease.getReason();
        this.manage = disease.getManage();
    }
}