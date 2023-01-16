package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.PostRequestDto;
import meong.nyang.dto.PostResponseDto;
import meong.nyang.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    //게시글 작성
    @PostMapping("/posts/{memberId}")
    public PostResponseDto createPost(@PathVariable Long memberId, @RequestBody PostRequestDto postRequestDto) {
        Long postId = postService.createPost(postRequestDto, memberId);
        PostResponseDto responseDto = postService.findPostByPostId(postId);
        return responseDto;
    }

    //게시글 수정
    @PutMapping("/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        Long updatePostId = postService.updatePost(postRequestDto, postId);
        PostResponseDto responseDto = postService.findPostByPostId(updatePostId);
        return responseDto;
    }

    //게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    //모든 게시글 보기
    @GetMapping("/posts")
    public List<PostResponseDto> findPosts() {
        List<PostResponseDto> responseDtoList = postService.findAllPosts();
        return responseDtoList;
    }

    //특정 게시글 보기
    @GetMapping("/posts/{postId}")
    public PostResponseDto findPost(@PathVariable Long postId) {
        PostResponseDto responseDto = postService.findPostByPostId(postId);
        return responseDto;
    }

    //인기 아가들 정보 가져오기
    @GetMapping("/posts/popular")
    public List<PostResponseDto> findPopularPost() {
        List<PostResponseDto> responseDtoList = postService.findBestPostByDate();
        return responseDtoList;
    }
}