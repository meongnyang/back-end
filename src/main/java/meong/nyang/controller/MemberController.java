package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import meong.nyang.domain.Member;
import meong.nyang.dto.LoginDto;
import meong.nyang.dto.LoginResponseDto;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.dto.MemberResponseDto;
import meong.nyang.repository.MemberRepository;
import meong.nyang.service.MemberService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    @PostMapping("/members")
    public ResponseEntity<MemberResponseDto> userSignUp(@RequestBody MemberRequestDto memberRequestDto) throws Exception {
        Optional<Member> findMember = memberRepository.findByEmail(memberRequestDto.getEmail());
        try {
            if (findMember.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Long memberId = memberService.signUp(memberRequestDto);
            MemberResponseDto responseDto = memberService.findMemberByMemberId(memberId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @PatchMapping("/members/updateNickName/{memberId}")
    public ResponseEntity<MemberResponseDto> updateNickName(@PathVariable Long memberId,
                                                            @RequestBody MemberRequestDto memberRequestDto, Member member) throws Exception {
        try {
            Long findMemberId = memberService.updateInfo(memberRequestDto, memberId);
            MemberResponseDto responseDto = memberService.findMemberByMemberId(findMemberId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //사진 수정
    @PatchMapping("/members/updatePhoto/{membersId}")
    public ResponseEntity<MemberResponseDto> updateImg(@PathVariable Long membersId,
                                                       @RequestBody MemberRequestDto memberRequestDto) throws Exception {
        try {
            if (memberRequestDto.getImg().length() != 0) {
                Long findMemberId = memberService.updateImg(memberRequestDto, membersId);
                MemberResponseDto memberResponseDto = memberService.findMemberByMemberId(findMemberId);
                return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
            } else {
                Long findMemberId = memberService.deleteImg(memberRequestDto, membersId);
                MemberResponseDto memberResponseDto = memberService.findMemberByMemberId(findMemberId);
                return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //회원 탈퇴
    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long memberId) throws Exception {
        try {
            memberService.deleteMember(memberId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //email정보로 id찾기
    @PostMapping("/members/findId")
    public ResponseEntity<String> findMemberIdByEmail(@RequestBody String email) throws Exception {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject value = (JSONObject) jsonParser.parse(email);
            Long memberId = memberService.findMemberIdByEmail((String) value.get("email"));
            String json = "{\"memberId\" : " + memberId + "}";
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //로그인
    @PostMapping("/members/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) throws Exception {
        try {
            LoginResponseDto login = memberService.login(loginDto);
            return new ResponseEntity<>(login, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/admin")
    public ResponseEntity<MemberResponseDto> adminSignUp(@RequestBody MemberRequestDto memberRequestDto) throws Exception {
        Optional<Member> findMember = memberRepository.findByEmail(memberRequestDto.getEmail());
        try {
            if (findMember.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            Long memberId = memberService.AdminSignUp(memberRequestDto);
            MemberResponseDto responseDto = memberService.findMemberByMemberId(memberId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}




