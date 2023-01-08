package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Efficacy {
    @Id
    @GeneratedValue
    @Column(name = "efficacyId")
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feedId")
    private Feed feed;
}
