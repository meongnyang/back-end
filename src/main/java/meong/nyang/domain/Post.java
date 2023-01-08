package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue
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
    private Date date;

    private String img;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;
}
