package meong.nyang.domain;

import lombok.*;
import meong.nyang.dto.CommentRequestDto;

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

    @Builder
    public static Comment toEntity(String contents, Member member, Post post){
        return Comment.builder()
                .contents(contents)
                .member(member)
                .post(post)
                .build();
    }

    public void update(String contents) {
        this.contents = contents;
    }
}