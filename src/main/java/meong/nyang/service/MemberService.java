package meong.nyang.service;

import meong.nyang.domain.Member;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.dto.MemberResponseDto;
import meong.nyang.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    //회원가입
   @Transactional
    public Long createMember(MemberRequestDto memberRequestDto) throws IOException, Exception {

       //Authority authority = new Authority("ROLE_USER");

      // System.out.println(authority.getAuthorityName());


       Authority authority = Authority.builder()
               .authorityName("ROLE_USER")
               .build();

       Member member = Member.toEntity(memberRequestDto.getPassword(), memberRequestDto.getEmail()
                , memberRequestDto.getNickname() + authority);

     /*  Member member = Member.builder()
                        .nickname(memberRequestDto.getNickname())
                        .email(memberRequestDto.getEmail())
                        .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                        .authorities(Collections.singleton(authority))
                        .build();*/

       System.out.println(member.getAuthorities());
       System.out.println("여ㅣ여깅겨이겨이거ㅣ여이겨ㅣㅇ겨");
       memberRepository.save(member);
       return member.getId();
    }


    //회원정보 수정 - 닉네임
    @Transactional
    public Long updateInfo(MemberRequestDto memberRequestDto, Long memberId) throws Exception {
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

    @Transactional
    public Long updateImg(MemberRequestDto memberRequestDto, Long memberId) throws Exception {
        Member findMember = memberRepository.findMemberById(memberId);
        findMember.updateImg(memberRequestDto.getImg());
        return findMember.getId();
    }

    @Transactional
    public Long deleteImg(MemberRequestDto memberRequestDto, Long memberId) throws Exception {
        Member findMember = memberRepository.findMemberById(memberId);
        findMember.deletePhoto(memberRequestDto.getImg());
        return findMember.getId();
    }

    @Transactional
    public void deleteMember(Long memberId) {
       memberRepository.deleteById(memberId);
    }
}






