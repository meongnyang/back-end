package meong.nyang.service;

import meong.nyang.domain.Member;
import meong.nyang.dto.MemberDto;
import meong.nyang.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //회원가입
    public void createMember(MemberDto memberDto) {

        Member member = Member.builder()
                .email(memberDto.getEmail())
                .img(memberDto.getImg())
                .nickname(memberDto.getNickname())
                .password(memberDto.getPassword())
                .build();

        validateDuplicateMember(member);

    }

    public void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findMemberByEmail(member.getEmail());
        if (member == findMember) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else {
            memberRepository.save(member);
        }
    }

}
