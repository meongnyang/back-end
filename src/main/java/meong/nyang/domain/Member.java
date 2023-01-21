package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Builder
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert

public class Member {
    @Id
    @Column(name = "memberId", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @Column(name = "activated")
    private boolean activated;


    @NotNull
    @Builder.Default
    private String img = "http://localhost/image/image.png";


/*    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();*/

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Conimal> conimals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

/*    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Species> species = new ArrayList<>();*/

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "memberId", referencedColumnName = "memberId")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;


    public Member(String password, String email, String nickname) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

  /*  public static Member toEntity(String password, String email, String nickname) {
        final Member member = new Member(password, email, nickname);
        return member;
    }*/

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateImg(String img) {
        this.img = img;
    }

    public void deletePhoto(String img) {
        this.img = "'http://localhost/image/image.png'";
    }


}