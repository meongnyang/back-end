package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
