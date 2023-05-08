package meong.nyang.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meong.nyang.domain.Member;
import meong.nyang.domain.Report;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequestDto {
    private Long attackerId;
    private String contents;

    public static Report toEntity(Member member, String contents) {
        return Report.builder()
                .member(member)
                .contents(String.valueOf(contents))
                .build();
    }
}
