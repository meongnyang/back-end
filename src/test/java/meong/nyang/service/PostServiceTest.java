package meong.nyang.service;

import meong.nyang.dto.MemberRequestDto;
import meong.nyang.dto.PostRequestDto;
import meong.nyang.dto.PostResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@Rollback(value = false)
public class PostServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;
    @Autowired
    LikesService likesService;

    @Test
    public void 게시글작성() throws Exception {
        //given
        MemberRequestDto member = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        Long memberId = memberService.createMember(member);
        PostRequestDto post = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        //when
        Long postId = postService.createPost(post, memberId);
        //then
        PostResponseDto p = postService.findPostByPostId(postId);
        assertThat(postId).isEqualTo(p.getPostId());
    }

    @Test
    public void 게시글수정() throws Exception {
        //given
        MemberRequestDto member = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        Long memberId = memberService.createMember(member);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        PostRequestDto post2 = PostRequestDto.builder()
                .type(2L)
                .title("울 귀여운 만두 보고가세용용~!~!")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요ㅋㅋㅋ나의사랑정만두")
                .build();
        Long post1Id = postService.createPost(post1, memberId);
        Long post2Id = postService.createPost(post2, memberId);
        //when
        PostRequestDto post3 = PostRequestDto.builder()
                .type(2L)
                .title("귀여운 울 만듀 보고가세용")
                .category(1L)
                .contents("히히히히히히히히히")
                .build();
        postService.updatePost(post3, post2Id);
        //then
        assertThat(post3.getTitle()).isEqualTo("귀여운 울 만듀 보고가세용");
    }

    @Test
    public void 게시글삭제() throws Exception {
        //given
        MemberRequestDto member = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        Long memberId = memberService.createMember(member);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        PostRequestDto post2 = PostRequestDto.builder()
                .type(2L)
                .title("울 귀여운 만두 보고가세용용~!~!")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요ㅋㅋㅋ나의사랑정만두")
                .build();
        PostRequestDto post3 = PostRequestDto.builder()
                .type(2L)
                .title("엔티티티티 프레즐 프레즐~")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요ㅋㅋㅋ나의사랑정만두!!!!!")
                .build();
        Long post1Id = postService.createPost(post1, memberId);
        Long post2Id = postService.createPost(post2, memberId);
        Long post3Id = postService.createPost(post3, memberId);
        //when
        postService.deletePost(post1Id);
        //then
        assertThat(2).isEqualTo(postService.findAllPosts().size());
    }

    @Test
    public void 게시글단건조회() throws Exception {
        //given
        MemberRequestDto member = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        Long memberId = memberService.createMember(member);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        Long post1Id = postService.createPost(post1, memberId);
        //when
        PostResponseDto p1 = postService.findPostByPostId(post1Id);
        //then
        assertThat(post1Id).isEqualTo(p1.getPostId());
    }

    @Test
    public void 인기게시글() throws Exception {
        //given
        MemberRequestDto member1 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        MemberRequestDto member2 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jungjyeon@naver.com")
                .build();
        MemberRequestDto member3 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("j_yeon@naver.com")
                .build();
        Long memberId1 = memberService.createMember(member1);
        Long memberId2 = memberService.createMember(member2);
        Long memberId3 = memberService.createMember(member3);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용!! 이거슨 인기게시글이 될 글 멍멍이")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        Long postId1 = postService.createPost(post1, memberId1);
        PostRequestDto post2 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        Long postId2 = postService.createPost(post2, memberId2);
        PostRequestDto post3 = PostRequestDto.builder()
                .type(2L)
                .title("울 귀여운 만두 보고가세용용 이거슨 인기게시글이 될 글 고냥이")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        Long postId3 = postService.createPost(post3, memberId2);
        PostRequestDto post4 = PostRequestDto.builder()
                .type(2L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        Long postId4 = postService.createPost(post4, memberId2);
        likesService.postLike(memberId1, postId1);
        likesService.postLike(memberId2, postId1);
        likesService.postLike(memberId3, postId1);
        likesService.postLike(memberId3, postId2);
        likesService.postLike(memberId3, postId3);
        likesService.postLike(memberId1, postId3);
        //when
        PostResponseDto postResponseDto1 = postService.findBestPostByDate(1L);
        PostResponseDto postResponseDto2 = postService.findBestPostByDate(2L);
        //then
        assertThat(1L).isEqualTo(postResponseDto1.getType());
        assertThat(2L).isEqualTo(postResponseDto2.getType());
    }
}