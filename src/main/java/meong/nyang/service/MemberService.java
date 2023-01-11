package meong.nyang.service;

import meong.nyang.domain.Member;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //회원가입
    public Long createMember(MemberRequestDto memberDto) {
        Member member = validateDuplicateMember(memberDto);
        return member.getId();
    }

    public Member validateDuplicateMember(MemberRequestDto memberRequestDto) {
        Member findMember = memberRepository.findMemberByEmail(memberRequestDto.getEmail());
        Member member = Member.toEntity(memberRequestDto.getPassword(), memberRequestDto.getEmail(), memberRequestDto.getNickname());

        if (member == findMember) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else {
            memberRepository.save(member);
            return member;
        }
    }

}
