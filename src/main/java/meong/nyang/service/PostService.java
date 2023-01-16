package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;
import meong.nyang.dto.PostRequestDto;
import meong.nyang.dto.PostResponseDto;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //게시글 작성
    @Transactional
    public Long createPost(PostRequestDto postRequestDto, Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        Post post = postRepository.save(Post.toEntity(postRequestDto.getCategory(), postRequestDto.getType(),
                postRequestDto.getTitle(), postRequestDto.getContents(), postRequestDto.getImg(), member));
        return post.getId();
    }

    //게시글 수정
    @Transactional
    public Long updatePost(PostRequestDto postRequestDto, Long postId) {
        Post post = postRepository.findById(postId).get();
        post.update(postRequestDto.getCategory(), postRequestDto.getType(), postRequestDto.getTitle(),
                postRequestDto.getContents(), postRequestDto.getImg());
        return post.getId();
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    //모든 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Post> list = postRepository.findAll();
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    //특정 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPostByPostId(Long postId) {
        Post post = postRepository.findById(postId).get();
        return new PostResponseDto(post);
    }

    //특정 게시글의 좋아요 개수 조회
    @Transactional(readOnly = true)
    public Long findByLikesByPost(Long postId) {
        Post post = postRepository.findById(postId).get();
        return post.getCount();
    }

    //오늘 날짜에 좋아요 수가 제일 많은 [1일 1자랑] 게시글 return
    @Transactional(readOnly = true)
    public List<PostResponseDto> findBestPostByDate() {
        String nowLocalDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        List<Post> posts = postRepository.findPostsByCreatedDateAndCategory(nowLocalDate, 3L);
        List<Post> dogs = new ArrayList<Post>();
        List<Post> cats = new ArrayList<Post>();
        List<Post> TodayBestConimals = new ArrayList<Post>();
        //해당 날짜의 모든 [1일 1자랑] 게시글 가져와서 강아지/고양이 분리해주기
        for(Post post: posts) {
            if(post.getType() == 1L) {
                dogs.add(post);
            } else if(post.getType() == 2L) {
                cats.add(post);
            }
        }

        //강아지 따로 고양이 따로 좋아요가 가장 많은 글 찾기
        TodayBestConimals.add(dogs.stream().max(Comparator.comparingLong(Post::getCount)).get());
        TodayBestConimals.add(cats.stream().max(Comparator.comparingLong(Post::getCount)).get());

        return TodayBestConimals.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
}