package meong.nyang.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.ReportRequestDto;
import meong.nyang.dto.ReportResponseDto;
import meong.nyang.repository.MemberRepository;
import meong.nyang.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final MemberRepository memberRepository;

    @PostMapping("/members/report/{memberId}")
    public ResponseEntity<ReportResponseDto> report (@PathVariable Long memberId,
                                                     @RequestBody ReportRequestDto reportRequestDto) throws Exception {
        try{
            ReportResponseDto report = reportService.report(memberId, reportRequestDto);
           // String json = "{\"count\" : " + count + "}";
            return new ResponseEntity<>(report, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
