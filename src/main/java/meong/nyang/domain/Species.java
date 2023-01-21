package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Species {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speciesId")
    private Long id;

    @NotNull
    private Long type;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL)
    private List<Conimal> conimals = new ArrayList<>();

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;*/
    @Builder
    public Species(Long type, String name) {
        this.type = type;
        this.name = name;
    }

}
