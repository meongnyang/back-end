package meong.nyang.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.RecordRequestDto;
import meong.nyang.dto.RecordResponseDto;
import meong.nyang.service.RecordService;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@AllArgsConstructor
public class RecordController {
    private final RecordService recordService;

    //건강기록등록
    @PostMapping("/records/{memberId}/{conimalId}")
    public RecordResponseDto createRecord(@PathVariable Long memberId,
                                          @PathVariable Long conimalId,
                                          @RequestBody RecordRequestDto recordRequestDto) {
        Long recordId = recordService.createRecord(memberId, conimalId, recordRequestDto);
        return recordService.findRecordByRecordId(recordId);
    }

    @PatchMapping("/records/update/{recordId}")
    public RecordResponseDto upDateRecord(@PathVariable Long recordId,
                                          @RequestBody RecordRequestDto recordRequestDto) {

        Long updateRecordId = recordService.updateRecord(recordId, recordRequestDto);
        return recordService.findRecordByRecordId(updateRecordId);

    }
    @GetMapping("/records/{recordId}")
    public RecordResponseDto getRecord(@PathVariable Long recordId) {
        return recordService.findRecordByRecordId(recordId);
    }
}
