package meong.nyang.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stationId")
    private Long id;
    private Double longitude;
    private Double latitude;
    private String name;
    @Builder
    public Station(Double longitude, Double latitude, String name) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
    }
}
