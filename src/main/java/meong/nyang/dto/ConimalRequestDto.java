package meong.nyang.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Member;
import meong.nyang.domain.Species;

import java.util.Date;

@Getter
@NoArgsConstructor
public class ConimalRequestDto {
    private Long type;
    private String name;
    private Long gender;
    private Long neutering;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date birth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date adopt;
    private String img;
    private String speciesName;

    @Builder
    public ConimalRequestDto(Long type, String name, Long gender, Long neutering, Date birth, Date adopt, String speciesName) {
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.neutering = neutering;
        this.birth = birth;
        this.adopt = adopt;
        this.speciesName = speciesName;
    }

    public static Conimal toEntity(Long type, String name, Long gender, Long neutering, Date birth, Date adopt, Member member, Species species){
        return Conimal.builder()
                .type(type)
                .name(name)
                .gender(gender)
                .neutering(neutering)
                .birth(birth)
                .adopt(adopt)
                .member(member)
                .species(species)
                .build();
    }
}
