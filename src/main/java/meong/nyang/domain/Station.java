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
    private Double x;
    private Double y;
    private String name;
    @Builder
    public Station(Double x, Double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }
}
