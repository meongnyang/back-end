package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.domain.Member;
import meong.nyang.dto.*;
import meong.nyang.exception.ErrorCode;
import meong.nyang.exception.ErrorResponse;
import meong.nyang.jwt.JwtFilter;
import meong.nyang.jwt.TokenProvider;
import meong.nyang.repository.MemberRepository;
import meong.nyang.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    //private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

   @PostMapping("/members")
    public ResponseEntity<MemberRequestDto> userSignUp(@RequestBody MemberRequestDto memberRequestDto) throws Exception {
        Optional<Member> findMember = memberRepository.findMemberByEmail(memberRequestDto.getEmail());
        try {
            if (findMember.isPresent()) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            memberService.signUp(memberRequestDto);
            return new ResponseEntity<>(memberRequestDto, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @PatchMapping("/members/update")
    public ResponseEntity<MemberUpdateDto> userUpdate(Authentication authentication, @RequestBody MemberUpdateDto memberUpdateDto) throws IOException {
        try {
            Member member = (Member) authentication.getPrincipal();
            memberService.userUpdate(member, memberUpdateDto);
            return new ResponseEntity<>(HttpStatus.OK);

        }  catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @DeleteMapping("/members/delete")
    public void deleteUser(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        memberService.deleteMember(member.getId());
    }

    //로그인
    @PostMapping("/members/login")
    public ErrorResponse<?> login(@RequestBody LoginDto loginDto) throws NoSuchElementException {
        try {
            ResponseLoginMemberDto member = memberService.login(loginDto);
            return new ErrorResponse<>(member);
        } catch (Exception e) {
            return new ErrorResponse<>(ErrorCode.ACCESS_DENIED_LOGIN);
        }
    }


}




