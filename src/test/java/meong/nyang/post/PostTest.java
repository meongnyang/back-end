package meong.nyang.post;

import meong.nyang.domain.Member;
import meong.nyang.domain.Post;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class PostTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시글작성() throws Exception {
        //Given
        Date nowDate = new Date();
        SimpleDateFormat Today = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Member member = Member.builder()
                .password(null)
                .nickname("만두냠")
                .email("jung_j_yeon@naver.com")
                .build();
        Post post1 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해")
                .img(null)
                .member(member)
                .build();
        Post post2 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!!!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해 라뷰라뷰류")
                .img(null)
                .member(member)
                .build();

        //When
        Member member1 = memberRepository.save(member);
        Post p1 = postRepository.save(post1);
        Post p2 = postRepository.save(post2);

        //Then
        Post p = postRepository.findById(p1.getId()).get();
        assertThat(p1.getTitle()).isEqualTo("울 귀여운 만둥이 보고가세요!");
        System.out.println(p1.getContents());
    }
    @Test
    void 게시글삭제() throws Exception {
        //given
        Date nowDate = new Date();
        SimpleDateFormat Today = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Member member = Member.builder()
                .password(null)
                .nickname("만두냠")
                .email("jung_j_yeon@naver.com")
                .build();
        Post post1 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해")
                .img(null)
                .member(member)
                .build();
        Post post2 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!!!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해 라뷰라뷰류")
                .img(null)
                .member(member)
                .build();
        Member m1 = memberRepository.save(member);
        Post p1 = postRepository.save(post1);
        Post p2 = postRepository.save(post2);
        //when
        postRepository.delete(postRepository.findById(1L).get());
        //then
    }

    @Test
    void 게시글조회() throws Exception {
        //given
        Date nowDate = new Date();
        SimpleDateFormat Today = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Member member = Member.builder()
                .password(null)
                .nickname("만두냠")
                .email("jung_j_yeon@naver.com")
                .build();
        Post post1 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해")
                .img(null)
                .member(member)
                .build();
        Post post2 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!!!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해 라뷰라뷰류")
                .img(null)
                .member(member)
                .build();
        //when
        Member m1 = memberRepository.save(member);
        Post p1 = postRepository.save(post1);
        Post p2 = postRepository.save(post2);
        //then
        System.out.println(postRepository.findAll());
        System.out.println(postRepository.findById(p1.getId()));
    }

    @Test
    void 게시글업데이트() throws Exception {
        //given
        Date nowDate = new Date();
        SimpleDateFormat Today = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Member member = Member.builder()
                .password(null)
                .nickname("만두냠")
                .email("jung_j_yeon@naver.com")
                .build();
        Post post1 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해")
                .img(null)
                .member(member)
                .build();
        Post post2 = Post.builder()
                .type(1L)
                .category(1L)
                .title("울 귀여운 만둥이 보고가세요!!!")
                .contents("웅냥냥 우리 귀여운 만두 너무너무 귀엽죠 우리집 내새끼 사랑해 라뷰라뷰류")
                .img(null)
                .member(member)
                .build();
        //when
        Member m1 = memberRepository.save(member);
        Post p1 = postRepository.save(post1);
        Post p2 = postRepository.save(post2);
        //then
        System.out.println(postRepository.findById(p1.getId()).toString());
    }
}