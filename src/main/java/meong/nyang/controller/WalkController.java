package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.WalkIndexResponseDto;
import meong.nyang.dto.WalkRequestDto;
import meong.nyang.service.WalkService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Log4j2
@RestController
@RequiredArgsConstructor
public class WalkController {
    private final WalkService walkService;
    @PostMapping("/walk/{category}")
    public ResponseEntity<WalkIndexResponseDto> findweather(@PathVariable int category, @RequestBody WalkRequestDto walkRequestDto) throws IOException, ParseException {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "소형견");
        map.put(2, "중형견");
        map.put(3, "대형견");
        WalkIndexResponseDto responseDto = walkService.walkIndex(walkRequestDto.getNx(), walkRequestDto.getNy(), map.get(category), walkRequestDto.getCity(), walkRequestDto.getDistrict());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
