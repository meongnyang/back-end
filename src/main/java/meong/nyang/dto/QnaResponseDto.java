package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Qna;

@Getter
public class QnaResponseDto {
    private Long qnaId;
    private String title;
    private String question;
    private String answer;

    public QnaResponseDto(Qna qna) {
        this.qnaId = qna.getId();
        this.title = qna.getTitle();
        this.question = qna.getQuestion();
        this.answer = qna.getAnswer();
    }
}
