package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Species;
import meong.nyang.dto.SpeciesResponseDto;
import meong.nyang.repository.SpeciesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeciesService {
    private final SpeciesRepository speciesRepository;

    //견종 or 묘종 데이터 가져오기
    public List<SpeciesResponseDto> findSpeciesByType(Long type){
        List<Species> list = speciesRepository.findSpeciesByType(type);
        return list.stream().map(SpeciesResponseDto::new).collect(Collectors.toList());
    }
}
