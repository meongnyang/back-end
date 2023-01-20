package meong.nyang.service;

import meong.nyang.domain.Member;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        MemberRequestDto member1 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        MemberRequestDto member2 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@gmail.com")
                .build();
        //when
       // Long memberId1 = memberService.createMember(member1);
        //Long memberId2 = memberService.createMember(member2);
        //Member findMember = memberRepository.findMemberById(memberId1);
        //then
       // Assertions.assertThat(member1.getEmail()).isEqualTo(findMember.getEmail());
        //Assertions.assertThat(memberId1).isEqualTo(findMember.getId());
    }
}