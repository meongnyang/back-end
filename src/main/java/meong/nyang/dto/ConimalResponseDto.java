package meong.nyang.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import meong.nyang.domain.Conimal;
import java.util.Date;

@Getter
public class ConimalResponseDto {
    private Long ConimalId;
    private Long memberId;
    private Long type;
    private String name;
    private String gender;
    private Long neutering;
    private String img;
    private String speciesName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date birth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date adopt;
    private Long ddaybirth;
    private Long ddayadopt;

    public ConimalResponseDto(Conimal conimal) {
        Date now = new Date();
        Long DdayBirth = 0L;
        Long DdayAdopt = 0L;
        if (conimal.getBirth().getTime() > now.getTime()) DdayBirth = 0L;
        else DdayBirth = ((now.getTime() - conimal.getBirth().getTime()) /1000)/(24*60*60);
        if (conimal.getAdopt().getTime() > now.getTime()) DdayAdopt = 0L;
        else DdayAdopt = ((now.getTime() - conimal.getAdopt().getTime()) /1000)/(24*60*60);
        this.ConimalId = conimal.getId();
        this.memberId = conimal.getMember().getId();
        this.type = conimal.getType();
        this.name = conimal.getName();
        this.gender = conimal.getGender();
        this.neutering = conimal.getNeutering();
        this.img = conimal.getImg();
        this.speciesName = conimal.getSpecies().getName();
        this.birth = conimal.getBirth();
        this.adopt = conimal.getAdopt();
        this.ddaybirth = DdayBirth;
        this.ddayadopt = DdayAdopt;
    }
}
