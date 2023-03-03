//package meong.nyang.service;
//
//import meong.nyang.dto.MemberRequestDto;
//import meong.nyang.dto.PostRequestDto;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.transaction.Transactional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//@RunWith(SpringRunner.class)
//public class LikesServiceTest {
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    PostService postService;
//    @Autowired
//    LikesService likesService;
//
//    @Test
//    public void 좋아요누르기() throws Exception {
//        //given
//        MemberRequestDto member1 = MemberRequestDto.builder()
//                .password("1234")
//                .nickname("만두온닝")
//                .email("jung_j_yeon@naver.com")
//                .build();
//        MemberRequestDto member2 = MemberRequestDto.builder()
//                .password("1234")
//                .nickname("만두온닝")
//                .email("jungjyeon@naver.com")
//                .build();
//        MemberRequestDto member3 = MemberRequestDto.builder()
//                .password("1234")
//                .nickname("만두온닝")
//                .email("j_yeon@naver.com")
//                .build();
//        Long memberId1 = memberService.createMember(member1);
//        Long memberId2 = memberService.createMember(member2);
//        Long memberId3 = memberService.createMember(member3);
//        PostRequestDto post1 = PostRequestDto.builder()
//                .type(1L)
//                .title("울 귀여운 만두 보고가세용용")
//                .category(1L)
//                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
//                .build();
//        Long postId1 = postService.createPost(post1, memberId1);
//        PostRequestDto post2 = PostRequestDto.builder()
//                .type(1L)
//                .title("울 귀여운 만두 보고가세용용")
//                .category(1L)
//                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
//                .build();
//        Long postId2 = postService.createPost(post2, memberId2);
//        //when
//        likesService.postLike(memberId1, postId1);
//        likesService.postLike(memberId2, postId1);
//        likesService.postLike(memberId3, postId1);
//        //then
//        assertThat(likesService.findByLikesByPost(postId1)).isEqualTo(3L);
//    }
//
//    @Test
//    public void 좋아요취소() throws Exception {
//        //given
//        MemberRequestDto member1 = MemberRequestDto.builder()
//                .password("1234")
//                .nickname("만두온닝")
//                .email("jung_j_yeon@naver.com")
//                .build();
//        MemberRequestDto member2 = MemberRequestDto.builder()
//                .password("1234")
//                .nickname("만두온닝")
//                .email("jungjyeon@naver.com")
//                .build();
//        MemberRequestDto member3 = MemberRequestDto.builder()
//                .password("1234")
//                .nickname("만두온닝")
//                .email("j_yeon@naver.com")
//                .build();
//        Long memberId1 = memberService.createMember(member1);
//        Long memberId2 = memberService.createMember(member2);
//        Long memberId3 = memberService.createMember(member3);
//        PostRequestDto post1 = PostRequestDto.builder()
//                .type(1L)
//                .title("울 귀여운 만두 보고가세용용")
//                .category(1L)
//                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
//                .build();
//        Long postId1 = postService.createPost(post1, memberId1);
//        PostRequestDto post2 = PostRequestDto.builder()
//                .type(1L)
//                .title("울 귀여운 만두 보고가세용용")
//                .category(1L)
//                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
//                .build();
//        Long postId2 = postService.createPost(post2, memberId2);
//        //when
//        likesService.postLike(memberId1, postId1);
//        likesService.postLike(memberId2, postId1);
//        likesService.postLike(memberId3,postId1);
//        likesService.postLike(memberId3, postId1);
//        //then
//        assertThat(likesService.findByLikesByPost(postId1)).isEqualTo(2L);
//    }
//}