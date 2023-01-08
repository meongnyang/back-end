package meong.nyang.domain;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private Long id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;
}