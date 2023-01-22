package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.DiseaseResponseDto;
import meong.nyang.service.DiseaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;

    //반려묘/반려견 특정 피부병 정보 가져오기
    @GetMapping("/disease/{diseaseId}/{type}")
    public DiseaseResponseDto findDiseaseByType(@PathVariable Long diseaseId, @PathVariable Long type) {
        DiseaseResponseDto responseDto = diseaseService.findDiseaseByType(diseaseId, type);
        return responseDto;
    }
}
