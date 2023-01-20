package meong.nyang.repository;

import meong.nyang.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpeciesRepository extends JpaRepository<Species, Long> {
    Species findSpeciesByName(String name);
    List<Species> findSpeciesByType(Long type);
}
