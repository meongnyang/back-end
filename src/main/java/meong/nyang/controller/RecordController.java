package meong.nyang.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.CommentResponseDto;
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
                                          @RequestBody RecordRequestDto recordRequestDto) throws Exception {
        Long recordId = recordService.createRecord(memberId, conimalId, recordRequestDto);
        RecordResponseDto responseDto = recordService.findRecordByRecordId(recordId);
        return responseDto;
    }

    @PatchMapping("/records/update/{recordId}")
    public RecordResponseDto upDateRecord(@PathVariable Long recordId,
                                          @RequestBody RecordRequestDto recordRequestDto) throws Exception  {

        Long updateRecordId = recordService.updateRecord(recordId, recordRequestDto);
        RecordResponseDto responseDto = recordService.findRecordByRecordId(updateRecordId);
        return responseDto;

    }
    @GetMapping("/records/{recordId}")
    public RecordResponseDto getRecord(@PathVariable Long recordId) {
      RecordResponseDto responseDto = recordService.findRecordByRecordId(recordId);
      return responseDto;
    }
}
