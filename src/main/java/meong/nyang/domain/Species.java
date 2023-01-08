package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Species {
    @Id
    @GeneratedValue
    @Column(name = "postId")
    private Long id;

    @NotNull
    private Long type;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="conimalId")
    private Conimal conimal;
}
