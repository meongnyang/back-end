package meong.nyang.dto;

import lombok.*;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Member;
import meong.nyang.domain.Record;

import java.util.concurrent.atomic.LongAccumulator;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecordRequestDto {
    private Long memberId;
    private Long conimalId;
    private Long meal;
    private Long voiding;
    private String voiding_reason;
    private Long excretion;
    private String excretion_reason;

    private String date;

    @Builder
    public RecordRequestDto(Long meal, Long voiding,String voiding_reason,
                            Long excretion,String excretion_reason, Long memberId, Long conimalId, String date, Record record) {
        this.meal = meal;
        this.voiding = voiding;
        this.voiding_reason = voiding_reason;
        this.excretion = excretion;
        this.excretion_reason = excretion_reason;
        this.memberId = memberId;
        this.conimalId = conimalId;
        this.date = record.getCreatedDate();
    }
    @Builder
    public static Record toEntity(Member member, Conimal conimal, Long meal, Long voiding, String voiding_reason,
                                  Long excretion, String excretion_reason) {
        return Record.builder()
                .meal(meal)
                .voiding(voiding)
                .voiding_reason(voiding_reason)
                .excretion(excretion)
                .excretion_reason(excretion_reason)
                .member(member)
                .conimal(conimal)
                .build();

    }

}
