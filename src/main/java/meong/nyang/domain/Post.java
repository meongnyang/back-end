package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @NotNull
    private Long category;

    @NotNull
    private Long type;

    @NotNull
    private Long count;

    @NotNull
    private String title;

    @NotNull
    private String contents;

    @NotNull
    private String date;

    private String img;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @Builder
    public Post(Long id, Long category, Long type, Long count, String title, String contents, String date, String img, Member member) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.count = count;
        this.title = title;
        this.contents = contents;
        this.date = date;
        this.img = img;
        this.member = member;
    }
}