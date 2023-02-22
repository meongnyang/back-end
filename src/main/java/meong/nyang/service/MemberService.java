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
        Optional<Member> findMember = memberRepository.findMemberByNickname(memberRequestDto.getNickname());
        Member member = memberRepository.findMemberById(memberId);
        if (findMember.isPresent() && !findMember.equals(memberId)) {
            throw new Exception("해당 닉네임이 존재합니다");
        } else {
            member.updateNickname(memberRequestDto.getNickname());
        }
        return member.getId();
    }
    //아이디로 회원 정보 찾기
    @Transactional(readOnly = true)
    public MemberResponseDto findMemberByMemberId(Long memberId) throws Exception{
        Optional<Member> findMember = Optional.ofNullable(memberRepository.findMemberById(memberId));
        if (findMember.isPresent()) {
            Member member = memberRepository.findMemberById(memberId);
            return new MemberResponseDto(member);
        } else {
            throw new Exception("회원 정보가 없습니다.");
        }
    }
    //회원 이미지 수정
    @Transactional
    public Long updateImg(MemberRequestDto memberRequestDto, Long memberId) throws Exception {
        Optional<Member> findMember = Optional.ofNullable(memberRepository.findMemberById(memberId));
        if (findMember.isEmpty()) {
            throw new Exception("회원 정보가 없습니다.");
        } else {
            Member member = memberRepository.findMemberById(memberId);
            member.updateImg(memberRequestDto.getImg());
            return member.getId();
        }
    }
    //회원 이미지 삭제
    @Transactional
    public Long deleteImg(MemberRequestDto memberRequestDto, Long memberId) throws Exception {
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            throw new Exception("회원 정보가 없습니다.");
        } else {
            Member member = memberRepository.findMemberById(memberId);
            member.deletePhoto();
            return member.getId();
        }
    }
    //회원 삭제
    @Transactional
    public void deleteMember(Long memberId) throws Exception {
       Optional<Member> findMember = memberRepository.findById(memberId);
       if (findMember.isEmpty()) {
           throw new Exception("회원 정보가 없습니다.");
       } else {
           memberRepository.deleteById(memberId);
       }
    }
    //이메일로 회원Id 찾기
    @Transactional(readOnly = true)
    public Long findMemberIdByEmail(String email) throws Exception{
        Optional<Member> findMember = Optional.ofNullable(memberRepository.findMemberByEmail(email));
        if (findMember.isEmpty()) {
            System.out.println(email);
            throw new Exception("회원 정보가 없습니다.");
        } else {
            Member member = memberRepository.findMemberByEmail(email);
            return member.getId();
        }
    }
}






