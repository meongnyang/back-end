package meong.nyang.repository;

import meong.nyang.domain.Disease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Disease findDiseaseByIdAndType(Long diseaseId, Long type);
}
