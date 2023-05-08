package meong.nyang.service;

import lombok.AllArgsConstructor;
import meong.nyang.domain.Member;
import meong.nyang.domain.Report;
import meong.nyang.dto.ReportRequestDto;
import meong.nyang.dto.ReportResponseDto;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class ReportService {

    private final MemberRepository memberRepository;
    private final ReportRepository reportRepository;

    //신고하기
    public ReportResponseDto report(Long memberId, ReportRequestDto reportRequestDto) throws Exception {
        Member victim = memberRepository.findMemberById(memberId);
        Member attacker = memberRepository.findMemberById(reportRequestDto.getAttackerId());
       if (victim.getId() == attacker.getId()) {
            throw new Exception("해당 신고자는 신고할 수 없습니다!");
        } else {
           String contents = reportRequestDto.getContents();
           attacker.updateReport(attacker.getReportCount() + 1l);

           Report report = Report.builder()
                   .contents(reportRequestDto.getContents())
                   .member(victim)
                   .attackerId(reportRequestDto.getAttackerId())
                   .build();

           reportRepository.save(report);
        }

       return new ReportResponseDto(
               reportRequestDto.getAttackerId(),
               reportRequestDto.getContents()
       );

    }

    @Transactional(readOnly = true)
    public Long findReportByMember(Long reportId) {
        Member member = memberRepository.findById(reportId).get();
        return member.getReportCount();
    }
}
