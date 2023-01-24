package meong.nyang.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Member;
import meong.nyang.dto.MemberResponseDto;
import meong.nyang.dto.MyPageResponseDto;
import meong.nyang.repository.ConimalRepository;
import meong.nyang.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final ConimalRepository conimalRepository;

    @Transactional
    public MyPageResponseDto getInfo(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        List<Conimal> conimals = conimalRepository.findConimalsByMemberId(memberId);
        return new MyPageResponseDto(member, conimals);
    }

}
