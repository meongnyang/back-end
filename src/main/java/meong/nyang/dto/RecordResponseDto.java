package meong.nyang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import meong.nyang.domain.Record;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecordResponseDto {
    private Long recordId;
    private Long meal;
    private Long voiding;
    private String voiding_reason;
    private Long excretion;
    private String excretion_reason;
    private String date;

    public RecordResponseDto(Record record) {
        Map<Long, String> meal = new HashMap<>() {{
            put(1L, "잘먹음");
            put(2L, "평소보다 적게먹음");
            put(3L, "안먹음");
        }};

        Map<Long,String> voiding = new HashMap<>() {{
            put(1L, "좋음");
            put(2L, "나쁨");
        }};

        Map<Long,String> excretion = new HashMap<>(){{
            put(1L,"좋음");
            put(2L,"나쁨");
        }};
        this.recordId = record.getId();
        this.meal = record.getMeal();
        this.voiding = record.getVoiding();
        this.voiding_reason = record.getVoiding_reason();
        this.excretion = record.getExcretion();
        this.excretion_reason = record.getExcretion_reason();
        this.date = record.getCreatedDate();
    }


}
