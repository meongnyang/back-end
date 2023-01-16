package meong.nyang.service;

import meong.nyang.dto.CommentRequestDto;
import meong.nyang.dto.MemberRequestDto;
import meong.nyang.dto.PostRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @Autowired
    CommentService commentService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostService postService;

    @Test
    public void 댓글작성() throws Exception {
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
        Long memberId1 = memberService.createMember(member1);
        Long memberId2 = memberService.createMember(member2);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
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
        CommentRequestDto comment1 = CommentRequestDto.builder()
                .contents("우왕 만두가정말정말 귀엽고 사랑스러운걸요???")
                .build();
        CommentRequestDto comment2 = CommentRequestDto.builder()
                .contents("웅냥냥 만두 너무 귀여워요 쏘 카와이")
                .build();
        //when
        Long commentId1 = commentService.createComment(comment1, memberId1, postId1);
        Long commentId2 = commentService.createComment(comment2, memberId2, postId1);
        //then
        assertThat(commentService.findCommentsByPostId(postId1).size()).isEqualTo(2L);
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        MemberRequestDto member1 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("jung_j_yeon@naver.com")
                .build();
        MemberRequestDto member2 = MemberRequestDto.builder()
                .password("1234")
                .nickname("만두온닝")
                .email("j_yeon@naver.com")
                .build();
        Long memberId1 = memberService.createMember(member1);
        Long memberId2 = memberService.createMember(member2);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
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
        CommentRequestDto comment1 = CommentRequestDto.builder()
                .contents("우왕 만두가정말정말 귀엽고 사랑스러운걸요???")
                .build();
        CommentRequestDto comment2 = CommentRequestDto.builder()
                .contents("웅냥냥 만두 너무 귀여워요 쏘 카와이")
                .build();
        Long commentId1 = commentService.createComment(comment1, memberId1, postId1);
        Long commentId2 = commentService.createComment(comment2, memberId2, postId1);
        //when
        CommentRequestDto comment3 = CommentRequestDto.builder()
                .contents("이거슨 수정된 댓글이에용")
                .build();
        commentService.updateComment(comment3, commentId2);
        //then
        assertThat(commentService.findCommentsByCommentsId(commentId2).getContents()).isEqualTo("이거슨 수정된 댓글이에용");
    }

    @Test
    public void 댓글삭제() throws Exception {
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
        Long memberId1 = memberService.createMember(member1);
        Long memberId2 = memberService.createMember(member2);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
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
        CommentRequestDto comment1 = CommentRequestDto.builder()
                .contents("우왕 만두가정말정말 귀엽고 사랑스러운걸요???")
                .build();
        CommentRequestDto comment2 = CommentRequestDto.builder()
                .contents("웅냥냥 만두 너무 귀여워요 쏘 카와이")
                .build();
        Long commentId1 = commentService.createComment(comment1, memberId1, postId1);
        Long commentId2 = commentService.createComment(comment2, memberId2, postId2);
        //when
        commentService.deleteComment(commentId1);
        //then
        assertThat(commentService.findAllComments().size()).isEqualTo(1);
    }

    @Test
    public void 특정게시글에달린댓글전부조회() throws Exception {
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
        Long memberId1 = memberService.createMember(member1);
        Long memberId2 = memberService.createMember(member2);
        PostRequestDto post1 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        PostRequestDto post2 = PostRequestDto.builder()
                .type(1L)
                .title("울 귀여운 만두 보고가세용용")
                .category(1L)
                .contents("울 귀여운 만두! 어때요 진짜 너무너무 귀엽죠 내새끼라그래요")
                .build();
        Long postId1 = postService.createPost(post1, memberId1);
        Long postId2 = postService.createPost(post2, memberId2);
        CommentRequestDto comment1 = CommentRequestDto.builder()
                .contents("우왕 만두가정말정말 귀엽고 사랑스러운걸요???")
                .build();
        CommentRequestDto comment2 = CommentRequestDto.builder()
                .contents("웅냥냥 만두 너무 귀여워요 쏘 카와이")
                .build();
        CommentRequestDto comment3 = CommentRequestDto.builder()
                .contents("웅냥냥 만두 너무 귀여워요 쏘 카와이")
                .build();
        //when
        Long commentId1 = commentService.createComment(comment1,memberId1, postId1);
        Long commentId2 = commentService.createComment(comment2, memberId2, postId1);
        Long commentId3 = commentService.createComment(comment3, memberId2, postId2);
        //then
        assertThat(commentService.findCommentsByPostId(postId1).size()).isEqualTo(2);
        assertThat(commentService.findCommentsByPostId(postId2).size()).isEqualTo(1);
    }
}