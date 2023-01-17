package meong.nyang.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.domain.Member;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.dto.MemberResponseDto;
import meong.nyang.repository.MemberRepository;
import meong.nyang.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/members")
    public ResponseEntity<MemberRequestDto> userSignUp(@RequestBody MemberRequestDto memberRequestDto) throws Exception {
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberRequestDto.getEmail());
        try {
            if (findMember.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            memberService.createMember(memberRequestDto);
            return new ResponseEntity<>(memberRequestDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PatchMapping("/members/updateNickName/{memberId}")
    public MemberResponseDto updateNickName(@PathVariable Long memberId,
                                            @RequestBody MemberRequestDto memberRequestDto, Member member) throws Exception {
        Long findMemberId = memberService.updateInfo(memberRequestDto, memberId);
        MemberResponseDto responseDto = memberService.findMemberByMemberId(findMemberId);

        return responseDto;
    }

    //사진 수정
    @PatchMapping("/members/updatePhoto/{membersId}")
    public MemberResponseDto updateImg(@PathVariable Long membersId,
                                       @RequestBody MemberRequestDto memberRequestDto) throws Exception {
        if (memberRequestDto.getImg().length() != 0) {
            Long findMemberId = memberService.updateImg(memberRequestDto, membersId);
            MemberResponseDto memberResponseDto = memberService.findMemberByMemberId(findMemberId);
            return memberResponseDto;
        } else {
            Long findMemberId = memberService.deleteImg(memberRequestDto, membersId);
            MemberResponseDto memberResponseDto = memberService.findMemberByMemberId(findMemberId);
            return memberResponseDto;
        }

    }

    @DeleteMapping("members/{memberId}")
    public void deleteUser (@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }
}




