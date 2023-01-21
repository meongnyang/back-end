package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import meong.nyang.domain.Species;

@Getter
public class SpeciesResponseDto {
    String name;

    @Builder
    public SpeciesResponseDto(Species species) {
        this.name = species.getName();
    }
}
