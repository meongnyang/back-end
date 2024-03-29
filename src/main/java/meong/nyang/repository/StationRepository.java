package meong.nyang.repository;

import meong.nyang.domain.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long>, CustomStationRepository {
}
