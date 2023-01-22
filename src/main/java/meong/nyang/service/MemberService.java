package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meong.nyang.controller.util.SecurityUtil;
import meong.nyang.domain.Authority;
import meong.nyang.domain.Member;
import meong.nyang.dto.*;
import meong.nyang.exception.DuplicateMemberException;
import meong.nyang.exception.NotFoundMemberException;
import meong.nyang.jwt.JwtFilter;
import meong.nyang.jwt.TokenProvider;
import meong.nyang.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;



    @Transactional
    public MemberRequestDto signUp(MemberRequestDto memberRequestDto) {
        if (memberRepository.findOneWithAuthoritiesByEmail(memberRequestDto.getEmail()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        System.out.println(authority.getAuthorityName());

        Member member = Member.builder()
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .nickname(memberRequestDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        System.out.println(authority.toString());
        System.out.println(member.getId());

        return memberRequestDto.from(memberRepository.save(member));
    }

    //email을 parameter로 받아서 어떠한 username이던 username에 해당하는 정보를 가져올 수 있다
/*    @Transactional(readOnly = true)
    public MemberRequestDto getUserWithAuthorities(String email) {
        return MemberRequestDto.from(memberRepository.findOneWithAuthoritiesByEmail(email).orElse(null));
    }*/
    //security context에 저장된 user만
/*    @Transactional(readOnly = true)
    public MemberRequestDto getMyUserWithAuthorities() {
        return MemberRequestDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(memberRepository::findOneWithAuthoritiesByEmail)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }*//*
   @Transactional
    public Long createMember(MemberRequestDto memberRequestDto) throws IOException, Exception {
       Member member = Member.toEntity(memberRequestDto.getPassword(), memberRequestDto.getEmail(), memberRequestDto.getNickname());
       memberRepository.save(member);
       return member.getId();*/

       //Authority authority = new Authority("ROLE_USER");

      // System.out.println(authority.getAuthorityName());


/*

  Member member = Member.builder()
                        .nickname(memberRequestDto.getNickname())
                        .email(memberRequestDto.getEmail())
                        .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                        .authorities(Collections.singleton(authority))
                        .build();
*/
    //}


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

    public ResponseLoginMemberDto login(LoginDto loginDto) throws Exception {
        TokenDto tokenResponseEntity = getTokenDtoResponseEntity(loginDto);
        Member member = memberRepository.findByEmail(loginDto.getEmail());
        return new ResponseLoginMemberDto(
                tokenResponseEntity.getToken(),
                member.getId(),
                member.getNickname(),
                member.getEmail());
    }

    private TokenDto getTokenDtoResponseEntity(LoginDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(member.getId(),authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new TokenDto(jwt);
    }

}






