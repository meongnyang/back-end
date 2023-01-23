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
    public DiseaseResponseDto findDiseaseByType(Long diseaseId, Long type) {
        Disease disease = diseaseRepository.findDiseaseByIdAndType(diseaseId, type);
        return new DiseaseResponseDto(disease);
    }
}