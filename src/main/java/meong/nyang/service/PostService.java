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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //게시글 작성
    @Transactional
    public Long createPost(PostRequestDto postRequestDto, Long memberId) throws Exception {
        Optional<Member> member = memberRepository.findById(memberId);
        if (member.isEmpty()) {
            throw new Exception("회원 정보가 없습니다.");
        } else {
            Member m = memberRepository.findMemberById(memberId);
            Post post = postRepository.save(postRequestDto.toEntity(postRequestDto.getCategory(), postRequestDto.getType(),
                    postRequestDto.getTitle(), postRequestDto.getContents(), postRequestDto.getImg(), m));
            return post.getId();
        }
    }

    //게시글 수정
    @Transactional
    public Long updatePost(PostRequestDto postRequestDto, Long postId) throws Exception {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new Exception("게시글이 존재하지 않습니다.");
        } else {
            Post post = postRepository.findById(postId).get();
            PostRequestDto dto = postRequestDto;
            if (dto.getCategory() != null) post.updateCategory(dto.getCategory());
            if (dto.getType() != null) post.updateType(dto.getType());
            if (dto.getImg() != null) post.updateImg(dto.getImg()); else post.deleteImg();
            if (dto.getTitle() != null) post.updateTitle(dto.getTitle());
            if (dto.getContents() != null ) post.updateContents(dto.getContents());
            return post.getId();
        }
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long postId) throws Exception {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            throw new Exception("게시글이 존재하지 않습니다.");
        } else {
            postRepository.deleteById(postId);
        }
    }

    //모든 게시글 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> findAllPosts() {
        List<Post> list = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    //특정 게시글 조회
    @Transactional(readOnly = true)
    public PostResponseDto findPostByPostId(Long postId) throws Exception {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new Exception("게시글이 존재하지 않습니다.");
        } else {
            Post post = postRepository.findById(postId).get();
            return new PostResponseDto(post);
        }
    }

    //오늘 날짜에 좋아요 수가 제일 많은 [1일 1자랑] 게시글 return
    @Transactional(readOnly = true)
    public PostResponseDto findBestPostByDate(Long type) throws Exception {
        String nowLocalDate = LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        List<Post> posts = postRepository.findPostsByCreatedDateAndCategoryAndType(nowLocalDate, 3L, type);
        if (posts.isEmpty()) {
            throw new Exception("게시글이 존재하지 않습니다.");
        } else {
            //오늘 날짜의 좋아요가 가장 많은 글 찾기
           Post bestPost = posts.stream().max(Comparator.comparingLong(Post::getCount)).get();
            return new PostResponseDto(bestPost);
        }
    }

    //게시글 제목으로 게시글 Id찾기
    @Transactional(readOnly = true)
    public Long findPostIdByTitle(String title) throws Exception{
        Optional<Post> findPost = Optional.ofNullable(postRepository.findPostByTitle(title));
        if (findPost.isEmpty()) {
            throw new Exception("게시글이 존재하지 않습니다.");
        } else {
            Post post = postRepository.findPostByTitle(title);
            return post.getId();
        }
    }
}