package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna {
    @Id
    @GeneratedValue
    @Column(name = "qnaId")
    private Long id;

    @NotNull
    private String title;

    private String question;

    @NotNull
    private String answer;

}
