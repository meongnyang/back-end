package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    private String password;

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    private String img;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Conimal> conimals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Species> species = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @Builder
    public Member(Long id, String password, String email, String nickname, String img, List<Post> posts) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.img = img;
        this.posts = posts;
    }

    @Builder
    public Member(Long id, String email, String nickname, String img, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.img = img;
        this.password = password;
    }
}