package meong.nyang.domain;

import javax.persistence.*;

@Entity
public class Likes {
    @Id
    @GeneratedValue
    @Column(name = "likesId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;
}
