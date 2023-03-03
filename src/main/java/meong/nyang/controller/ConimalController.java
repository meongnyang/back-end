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
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ConimalController {
    private final ConimalService conimalService;

    //반려동물 정보 등록
    @PostMapping("/conimals/{memberId}")
    public ResponseEntity<ConimalResponseDto> createConimal(@PathVariable Long memberId, @RequestBody ConimalRequestDto conimalRequestDto) throws Exception {
        //올바르지 않은 memberId로 정보를 등록하려고 할때 에러
        try {
            Long conimalId = conimalService.createConimal(conimalRequestDto, memberId);
            ConimalResponseDto responseDto = conimalService.findConimalById(conimalId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //반려동물 정보 수정
    @PatchMapping("/conimals/{conimalId}")
    public ResponseEntity<ConimalResponseDto> updateConimal(@PathVariable Long conimalId, @RequestBody ConimalRequestDto conimalRequestDto) throws Exception{
        try {
            Long updateConimalId = conimalService.updateConimal(conimalRequestDto, conimalId);
            ConimalResponseDto responseDto = conimalService.findConimalById(updateConimalId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //반려동물 정보 삭제
    @DeleteMapping("/conimals/{conimalId}")
    public ResponseEntity<?> deleteConimal(@PathVariable Long conimalId) throws Exception{
        //올바르지 않은 ConimalId로 삭제하려고 할 때 에러
        Optional<ConimalResponseDto> conimal = Optional.ofNullable(conimalService.findConimalById(conimalId));
        if (conimal.isPresent()) {
            conimalService.deleteConimal(conimalId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //특정회원의 모든 반려동물 정보
    @GetMapping("/conimals/all/{memberId}")
    public List<ConimalResponseDto> findConimalsByMemberId(@PathVariable Long memberId){
        List<ConimalResponseDto> responseDtoList = conimalService.findConimalsBymemberId(memberId);
        return responseDtoList;
    }
    //특정 반려동물 정보
    @GetMapping("/conimals/{conimalId}")
    public ResponseEntity<ConimalResponseDto> findConimal(@PathVariable Long conimalId) throws Exception{
        try {
            ConimalResponseDto responseDto = conimalService.findConimalById(conimalId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
