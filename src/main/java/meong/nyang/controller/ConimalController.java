package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.ConimalRequestDto;
import meong.nyang.dto.ConimalResponseDto;
import meong.nyang.service.ConimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ConimalController {
    private final ConimalService conimalService;

    //반려동물 정보 등록
    @PostMapping("/conimals/{memberId}")
    public ResponseEntity<ConimalResponseDto> createConimal(@PathVariable Long memberId, @RequestBody ConimalRequestDto conimalRequestDto) {
        Long conimalId = conimalService.createConimal(conimalRequestDto, memberId);
        ConimalResponseDto responseDto = conimalService.findConimalById(conimalId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
    //반려동물 정보 수정
    @PatchMapping("/conimals/{conimalId}")
    public ResponseEntity<ConimalResponseDto> updateConimal(@PathVariable Long conimalId, @RequestBody ConimalRequestDto conimalRequestDto) {
        Long updateConimalId = conimalService.updateConimal(conimalRequestDto, conimalId);
        ConimalResponseDto responseDto = conimalService.findConimalById(updateConimalId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    //반려동물 정보 삭제
    @DeleteMapping("/conimals/{conimalId}")
    public ResponseEntity<?> deleteConimal(@PathVariable Long conimalId) {
        conimalService.deleteConimal(conimalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //특정회원의 모든 반려동물 정보
    @GetMapping("/conimals/all/{memberId}")
    public List<ConimalResponseDto> findConimalsByMemberId(@PathVariable Long memberId) {
        List<ConimalResponseDto> responseDtoList = conimalService.findConimalsBymemberId(memberId);
        return responseDtoList;
    }
    //특정 반려동물 정보
    @GetMapping("/conimals/{conimalId}")
    public ResponseEntity<ConimalResponseDto> findConimal(@PathVariable Long conimalId) {
        ConimalResponseDto responseDto = conimalService.findConimalById(conimalId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
