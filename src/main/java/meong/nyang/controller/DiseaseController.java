package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.DiseaseResponseDto;
import meong.nyang.service.DiseaseService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class DiseaseController {
    private final DiseaseService diseaseService;

    //반려묘/반려견 특정 피부병 정보 가져오기
    @PostMapping("/disease")
    public DiseaseResponseDto findDiseaseByType(@RequestBody String name) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject value = (JSONObject) jsonParser.parse(name);
        DiseaseResponseDto responseDto = diseaseService.findDiseaseByName((String) value.get("name"));
        return responseDto;
    }
}