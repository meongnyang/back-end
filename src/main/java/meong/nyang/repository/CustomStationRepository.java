package meong.nyang.repository;

import meong.nyang.domain.Station;

public interface CustomStationRepository {
    String findStationByLocation(Double longitude, Double latitude);
}
