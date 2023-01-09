package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {
    @Id
    @GeneratedValue
    @Column(name = "feedId")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long type;

    @NotNull
    private String material;

    @NotNull
    private String ingredient;

    private String img;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL)
    private List<Efficacy> efficacyList = new ArrayList<>();

}
