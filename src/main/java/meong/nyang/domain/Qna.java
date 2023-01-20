package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnaId")
    private Long id;
    @Column(length = 65535)
    @NotNull
    private String title;
    @Column(length = 65535)
    private String question;
    @Column(length = 65535)
    @NotNull
    private String answer;

    @Builder
    public Qna(String title, String question, String answer) {
        this.title = title;
        this.question = question;
        this.answer = answer;
    }
}
