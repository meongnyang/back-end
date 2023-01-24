package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Species;

import java.util.Date;

@Getter
public class MyPageConimalResponseDto {

    private String name;
    private Long gender;
    private Species species;
    private Long ddaybirth;
    private Long ddayadopt;
    private String conimalImg;

    public MyPageConimalResponseDto(Conimal conimal) {
        Date now = new Date();
        Long DdayBirth = 0L;
        Long DdayAdopt = 0L;
        if (conimal.getBirth().getTime() > now.getTime()) DdayBirth = 0L;
        else DdayBirth = ((now.getTime() - conimal.getBirth().getTime()) /1000)/(24*60*60);
        if (conimal.getAdopt().getTime() > now.getTime()) DdayAdopt = 0L;
        else DdayAdopt = ((now.getTime() - conimal.getAdopt().getTime()) /1000)/(24*60*60);

        this.name = conimal.getName();
        this.gender = conimal.getGender();
        this.species = conimal.getSpecies();
        this.ddaybirth = DdayBirth;
        this.ddayadopt = DdayAdopt;
        this.conimalImg = conimal.getImg();
    }
}
