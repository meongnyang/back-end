package meong.nyang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Conimal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conimalId")
    private Long id;
    @NotNull
    private Long type;
    @NotNull
    private String name;
    @NotNull
    private String gender;
    @NotNull
    private Long neutering;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    Date birth;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @NotNull
    Date adopt;
    private String img = "https://meongnyang.s3.ap-northeast-2.amazonaws.com/conimal/pet_profile.png";
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="memberId")
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name="speciesId")
    private Species species;
    @OneToMany(mappedBy = "conimal", cascade = CascadeType.ALL)
    private List<Record> recordList = new ArrayList<>();
    private Long category;

    @Builder
    public Conimal(Long type, String name, String gender, Long neutering, Date birth, Date adopt, Member member, Species species, Long category) {
        this.type = type;
        this.name = name;
        this.gender = gender;
        this.neutering = neutering;
        this.birth = birth;
        this.adopt = adopt;
        this.member = member;
        this.species = species;
        this.category = category;
    }

    public void updateType(Long type) {
        this.type = type;
    }
    public void updateName(String name) {
        this.name = name;
    }
    public void updateGender(String gender) {
        this.gender = gender;
    }
    public void updateNeutering(Long neutering) {
        this.neutering = neutering;
    }
    public void updateBirth(Date birth) {
        this.birth = birth;
    }
    public void updateAdopt(Date adopt) {
        this.adopt = adopt;
    }
    public void updateImg(String img) {
        this.img = img;
    }
    public void deleteImg() { this.img = "https://meongnyang.s3.ap-northeast-2.amazonaws.com/conimal/pet_profile.png"; }
    public void updateSpecies(Species species) {
        this.species = species;
    }
    public void updateCategory(Long category) {
        this.category = category;
    }
}