package meong.nyang.service;

import lombok.AllArgsConstructor;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Member;
import meong.nyang.domain.Record;
import meong.nyang.dto.RecordRequestDto;
import meong.nyang.dto.RecordResponseDto;
import meong.nyang.repository.ConimalRepository;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RecordService {
    private MemberRepository memberRepository;
    private RecordRepository recordRepository;
    private ConimalRepository conimalRepository;

    //건강 기록 등록
    @Transactional
    public Long createRecord(Long memberId, Long conimalId, RecordRequestDto recordRequestDto) throws Exception {
        String nowLocalDate = LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        Optional<Record> record = recordRepository.findRecordByCreatedDateAndMemberId(nowLocalDate, memberId);
        Member member = memberRepository.findMemberById(memberId);
        Conimal conimal = conimalRepository.findConimalsById(conimalId);
        if (record.isPresent()) {
            throw new Exception("이미 오늘 건강 기록을 등록하셨습니다");
        } else {
            Record new_record = recordRepository.save(recordRequestDto.toEntity(member, conimal, recordRequestDto.getMeal(),
                    recordRequestDto.getVoiding(), recordRequestDto.getVoiding_reason(), recordRequestDto.getExcretion(),
                    recordRequestDto.getExcretion_reason()));
            return new_record.getId();
        }
    }
    @Transactional(readOnly = true)
    public RecordResponseDto findRecordByRecordId(Long recordId) {
        Record record = recordRepository.findById(recordId).get();
        return new RecordResponseDto(record);
    }

    @Transactional
    public Long updateRecord(Long recordId, RecordRequestDto recordRequestDto) {
       Record record = recordRepository.findById(recordId).get();
       record.update(recordRequestDto.getMeal(), recordRequestDto.getVoiding(), recordRequestDto.getVoiding_reason()
               ,recordRequestDto.getExcretion_reason(),recordRequestDto.getExcretion());
       return record.getId();
    }
    @Transactional(readOnly = true)
    public RecordResponseDto getRecord(Long recordId) {
        Record findRecord = recordRepository.findById(recordId).get();
        return new RecordResponseDto(findRecord);
    }
}
