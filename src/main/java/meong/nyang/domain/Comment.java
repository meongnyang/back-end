package meong.nyang.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long id;
    @Column(length = 65535)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;

    @Builder
    public Comment(String contents, Member member, Post post) {
        this.contents = contents;
        this.member = member;
        this.post = post;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}