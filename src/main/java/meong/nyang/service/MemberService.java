package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import meong.nyang.domain.Authority;
import meong.nyang.domain.Member;
import meong.nyang.dto.*;
import meong.nyang.jwt.JwtFilter;
import meong.nyang.jwt.PasswordUtil;
import meong.nyang.jwt.TokenProvider;
import meong.nyang.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();



    @Transactional
    public Long signUp(MemberRequestDto memberRequestDto) throws Exception {
        Optional<Member> signMember = memberRepository.findByEmail(memberRequestDto.getEmail());
        String password = memberRequestDto.getPassword();
        if(signMember.isPresent()){
            throw new Exception("이미 존재하는 회원입니다");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        Member member = Member.builder()
                .nickname(memberRequestDto.getNickname())
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(password))
                .authorities(Collections.singleton(authority))
                .reportCount(0L)
                .activated(true)
                .build();

        memberRequestDto.from(memberRepository.save(member));

        return member.getId();
    }

    @Transactional
    public Long AdminSignUp(MemberRequestDto memberRequestDto) throws Exception {
        Optional<Member> signMember = memberRepository.findByEmail(memberRequestDto.getEmail());
        String password = memberRequestDto.getPassword();
        if(signMember.isPresent()){
            throw new Exception("이미 존재하는 관리자 입니다");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_ADMIN")
                .build();

        Member member = Member.builder()
                .nickname(memberRequestDto.getNickname())
                .email(memberRequestDto.getEmail())
                .password(passwordEncoder.encode(password))
                .authorities(Collections.singleton(authority))
                .reportCount(0L)
                .activated(true)
                .build();

        memberRequestDto.from(memberRepository.save(member));

        return member.getId();
    }

    public LoginResponseDto login(LoginDto loginDto){
        Optional<Member> member = memberRepository.findByEmail(loginDto.getEmail());
        String password = loginDto.getPassword();

        if (password == null){
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(loginDto.getEmail())
                .password(password)
                .authorities("ROLE_USER")
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        TokenDto tokenDtoResponseEntity = getTokenDtoResponseEntity(loginDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new LoginResponseDto(
                tokenDtoResponseEntity.getToken(),
                member.get().getNickname(),
                member.get().getId(),
                member.get().getEmail());

    }


    private TokenDto getTokenDtoResponseEntity(LoginDto loginDto) {
        Member member = memberRepository.findMemberByEmail(loginDto.getEmail());
        String password = loginDto.getPassword();

        if (password == null){
            password = member.getPassword();
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());
        //Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getEmail()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(member.getId(), authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new TokenDto(jwt);
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
            throw new Exception("회원 정보가 없습니다.");
        } else {
            Member member = memberRepository.findMemberByEmail(email);
            return member.getId();
        }
    }

}






