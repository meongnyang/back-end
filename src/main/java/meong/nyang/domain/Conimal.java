package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conimal {
    @Id
    @GeneratedValue
    @Column(name = "conimalId")
    private Long id;

    @NotNull
    private Long type;

    @NotNull
    private String name;

    @NotNull
    private Long sex;

    private Date birth;

    @NotNull
    private Date adopt;

    private String img;

    private Long neutering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @OneToMany(mappedBy = "conimal", cascade = CascadeType.ALL)
    private List<Species> speciesList = new ArrayList<>();

    @OneToMany(mappedBy = "conimal", cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();

}
