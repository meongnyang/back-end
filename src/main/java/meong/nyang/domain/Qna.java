package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnaId")
    private Long id;
    @Column(columnDefinition = "TEXT", length = 65535)
    @NotNull
    private String title;
    @Column(columnDefinition = "TEXT", length = 65535)
    private String question;
    @Column(columnDefinition = "TEXT", length = 65535)
    @NotNull
    private String answer;

    @Builder
    public Qna(String title, String question, String answer) {
        this.title = title;
        this.question = question;
        this.answer = answer;
    }
}
