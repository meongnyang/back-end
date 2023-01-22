package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
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

    @NotNull
    @ColumnDefault("'http://localhost/image/image.png'")
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
    private List<Record> records = new ArrayList<>();

    @Builder
    public Member(String password, String email, String nickname) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
    }

    @Builder
    public static Member toEntity(String password, String email, String nickname){
        return Member.builder()
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }

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