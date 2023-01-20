package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.ConimalRequestDto;
import meong.nyang.dto.ConimalResponseDto;
import meong.nyang.service.ConimalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class ConimalController {
    private final ConimalService conimalService;

    //반려동물 정보 등록
    @PostMapping("/conimals/{memberId}")
    public ConimalResponseDto createConimal(@PathVariable Long memberId, @RequestBody ConimalRequestDto conimalRequestDto){
        Long conimalId = conimalService.createConimal(conimalRequestDto, memberId);
        ConimalResponseDto responseDto = conimalService.findConimalById(conimalId);
        return responseDto;
    }
    //반려동물 정보 수정
    @PatchMapping("/conimals/{conimalId}")
    public ConimalResponseDto updateConimal(@PathVariable Long conimalId, @RequestBody ConimalRequestDto conimalRequestDto){
        Long updateConimalId = conimalService.updateConimal(conimalRequestDto, conimalId);
        ConimalResponseDto responseDto = conimalService.findConimalById(updateConimalId);
        return responseDto;
    }
    //반려동물 정보 삭제
    @DeleteMapping("/conimals/{conimalId}")
    public void deleteConimal(@PathVariable Long conimalId) {
        conimalService.deleteConimal(conimalId);
    }
    //특정회원의 모든 반려동물 정보
    @GetMapping("/conimals/all/{memberId}")
    public List<ConimalResponseDto> findConimalsByMemberId(@PathVariable Long memberId){
        List<ConimalResponseDto> responseDtoList = conimalService.findConimalsBymemberId(memberId);
        return responseDtoList;
    }
    //특정 반려동물 정보
    @GetMapping("/conimals/{conimalId}")
    public ConimalResponseDto findConimal(@PathVariable Long conimalId){
        ConimalResponseDto responseDto = conimalService.findConimalById(conimalId);
        return responseDto;
    }
}
