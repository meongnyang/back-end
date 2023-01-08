package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
