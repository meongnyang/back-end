package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@DynamicInsert
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @NotNull
    private Long category;

    @NotNull
    private Long type;

    @NotNull
    private Long count = 0L;
    @Column(length = 65535)
    @NotNull
    private String title;
    @Column(length = 65535)
    @NotNull
    private String contents;

    private String img;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @Builder
    public Post(Long category, Long type, String title, String contents, String img, Member member) {
        this.category = category;
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.img = img;
        this.member = member;
    }

    public void update(Long category, Long type, String title, String contents, String img) {
        this.category = category;
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.img = img;
    }

    public void updateLikes(Long count) {
        this.count = count;
    }
}