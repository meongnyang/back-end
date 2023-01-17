package meong.nyang.service;

import meong.nyang.domain.Member;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.dto.MemberResponseDto;
import meong.nyang.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long createMember(MemberRequestDto memberRequestDto) {
        Member member = Member.toEntity(memberRequestDto.getPassword(), memberRequestDto.getEmail(), memberRequestDto.getNickname());
        memberRepository.save(member);
        return member.getId();
    }

    //회원정보 수정 - 닉네임
    @Transactional
    public Long updateInfo(MemberRequestDto memberRequestDto, Long memberId) throws Exception {
        //validateDuplicateNickName(memberId);
        //Optional<Member> findMember = memberRepository.findMemberById(memberId);
        Optional<Member> findMember = memberRepository.findMemberByNickname(memberRequestDto.getNickname());
        Member member = memberRepository.findMemberById(memberId);
        if (findMember.isPresent() && !findMember.equals(memberId)) {
            throw new Exception("해당 닉네임이 존재합니다");
        } else {
            member.updateNickname(memberRequestDto.getNickname());
        }
        return member.getId();
    }


    @Transactional
    public MemberResponseDto findMemberByMemberId(Long memberId) {
        Member member = memberRepository.findMemberById(memberId);
        return new MemberResponseDto(member);
    }
}






