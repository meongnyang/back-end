package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.SpeciesResponseDto;
import meong.nyang.service.SpeciesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SpeciesController {
    private final SpeciesService speciesService;

    @GetMapping("/species/{type}")
    public List<SpeciesResponseDto> findSpeciesByType(@PathVariable Long type) {
        return speciesService.findSpeciesByType(type);
    }
}
