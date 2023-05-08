package meong.nyang.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//@ToString(exclude = "conimal")
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

    @Column(name ="activated")
    private boolean activated;

    @JsonIgnore
    private Long reportCount = 0L;



    @NotNull
    @Builder.Default
    private String img = "https://meongnyang.s3.ap-northeast-2.amazonaws.com/person/profile.png";

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Conimal> conimals = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Record> records = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Report> reports = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "memberId", referencedColumnName = "memberId")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<Authority> authorities;



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


    public Member(Long id, String password, String email, String nickname, boolean activated, Set<Authority> authorities, Long reportCount) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.activated = activated;
        this.authorities = authorities;
        this.reportCount = reportCount;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateImg(String img) {
        this.img = img;
    }

    public void deletePhoto() {
        this.img = "https://meongnyang.s3.ap-northeast-2.amazonaws.com/person/profile.png";
    }

    public void updateReport(Long reportCount) {
        this.reportCount = reportCount;
    }

}