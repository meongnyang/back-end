package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.domain.Member;
import meong.nyang.dto.*;
import meong.nyang.jwt.JwtFilter;
import meong.nyang.jwt.TokenProvider;
import meong.nyang.repository.MemberRepository;
import meong.nyang.service.CustomUserDetailsService;
import meong.nyang.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

/*   @PostMapping("/members")
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

    }*/

    @GetMapping("/members")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<MemberRequestDto> getMyUserInfo(HttpServletRequest request) {
        return ResponseEntity.ok(memberService.getMyUserWithAuthorities());
    }

    //회원 가입
    @PostMapping("/members")
    public ResponseEntity<MemberRequestDto> signup(
            @Valid @RequestBody MemberRequestDto memberRequestDto
    ) {
        return ResponseEntity.ok(memberService.signUp(memberRequestDto));
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
    public void deleteUser(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
    }

   /* //로그인
    @PostMapping("/members/login")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }*/

    //로그인
    @PostMapping("/members/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) throws NoSuchElementException {
        try {
            ResponseLoginMemberDto member = memberService.login(loginDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}




