package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Disease;
import meong.nyang.dto.DiseaseResponseDto;
import meong.nyang.repository.DiseaseRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiseaseService {
    private final DiseaseRepository diseaseRepository;

    //특정 피부병 정보 가져오기
    public DiseaseResponseDto findDiseaseByName(String name) {
        Disease disease = diseaseRepository.findDiseaseByName(name);
        return new DiseaseResponseDto(disease);
    }
}