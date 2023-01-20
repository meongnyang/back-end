package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Qna;
import meong.nyang.dto.QnaResponseDto;
import meong.nyang.repository.QnaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;
    //지식인 글 전부 가져오기
    public List<QnaResponseDto> findAllQna() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Qna> list = qnaRepository.findAll(sort);
        return list.stream().map(QnaResponseDto::new).collect(Collectors.toList());

    }
    //특정 지식인 글 가져오기
    public QnaResponseDto findQnaByQnaId(Long qnaId) {
        Qna qna = qnaRepository.findById(qnaId).get();
        return new QnaResponseDto(qna);
    }
}
