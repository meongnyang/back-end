package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.QnaResponseDto;
import meong.nyang.service.QnaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;
    //지식인 글 전부 가져오기
    @GetMapping("/qna")
    public List<QnaResponseDto> findQna() {
        List<QnaResponseDto> responseDtoList = qnaService.findAllQna();
        return responseDtoList;
    }
    //특정 지식인 글 가져오기
    @GetMapping("/qna/{qnaId}")
    public QnaResponseDto findQna(@PathVariable Long qnaId) {
        QnaResponseDto responseDto = qnaService.findQnaByQnaId(qnaId);
        return responseDto;
    }
}
