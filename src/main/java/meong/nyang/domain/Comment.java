package meong.nyang.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentId")
    private Long id;
    @Column(columnDefinition = "TEXT", length = 65535)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId")
    private Comment parent;

    private boolean isRemoved= false;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();


    @Builder
    public Comment(String contents, Member member, Post post, Comment parent) {
        this.contents = contents;
        this.member = member;
        this.post = post;
        this.parent = parent;
        this.isRemoved = false;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}