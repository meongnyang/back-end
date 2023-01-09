package meong.nyang.service;

import meong.nyang.domain.Member;
import meong.nyang.dto.MemberDto;
import meong.nyang.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testMember(){
        //given
        Member member1 = new Member(1L, "wnstj1128@naver.com", "이월이", "1234", "wnstj1819");
        Member member2 = new Member(2L,"wnstj1819@naver.com","가을이","1234","wnstj7214");
        //when
        memberService.createMember(MemberDto.of(member1));
        memberService.createMember(MemberDto.of(member2));

        Member findMember = memberRepository.findMemberByEmail("wnstj1128@naver.com");

        //then
        Assertions.assertThat(member1.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(member1.getEmail()).isEqualTo(findMember.getEmail());
    }
}