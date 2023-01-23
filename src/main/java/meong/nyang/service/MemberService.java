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

        Member member = Member.builder()
                .nickname(memberRequestDto.getNickname())
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(memberRequestDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return memberRequestDto.from(memberRepository.save(member));
    }

    public ResponseLoginMemberDto login(LoginDto loginDto) throws Exception {
        Optional<Member> loginMember = memberRepository.findMemberByEmail(loginDto.getEmail());
        if ((loginMember.orElse(null) == null) || !passwordEncoder.matches(loginDto.getPassword(), loginMember.get().getPassword())) {
            throw new Exception("아이디와 비밀번호가 일치하지 않음");
        }
        else {
            Member member = loginMember.get();
            TokenDto tokenDtoResponseEntity = getTokenDtoResponseEntity(loginDto);
            return new ResponseLoginMemberDto(
                    tokenDtoResponseEntity.getToken(),
                    member.getId(),
                    member.getNickname(),
                    member.getEmail());
        }

    }

    private TokenDto getTokenDtoResponseEntity(LoginDto loginDto) {
        Member member = memberRepository.findByEmail(loginDto.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(member.getId(), authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new TokenDto(jwt);
    }

        //회원정보 수정
        public void userUpdate (Member member, MemberUpdateDto memberUpdateDto) throws Exception {
            Optional<Member> findMember = memberRepository.findMemberByNickname(memberUpdateDto.getNickname());
            if (findMember.isPresent() && !member.getNickname().equals(memberUpdateDto.getNickname())) {
                throw new Exception("아이디가 존재합니다.");
            } else {
                memberUpdateDto.setNickname(memberUpdateDto.getNickname());
                member.updateNickname(memberUpdateDto.getNickname());
            } if (memberUpdateDto.getImg().length() != 0) {
                memberUpdateDto.setImg(memberUpdateDto.getImg());
                member.updateImg(memberUpdateDto.getImg());
            } else {
                member.deletePhoto(memberUpdateDto.getImg());
            }

            memberRepository.save(member);
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






