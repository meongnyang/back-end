package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.PostRequestDto;
import meong.nyang.dto.PostResponseDto;
import meong.nyang.service.PostService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    //게시글 작성
    @PostMapping("/posts/{memberId}")
    public ResponseEntity<PostResponseDto> createPost(@PathVariable Long memberId, @RequestBody PostRequestDto postRequestDto) {
        Long postId = postService.createPost(postRequestDto, memberId);
        PostResponseDto responseDto = postService.findPostByPostId(postId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        Long updatePostId = postService.updatePost(postRequestDto, postId);
        PostResponseDto responseDto = postService.findPostByPostId(updatePostId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //모든 게시글 보기
    @GetMapping("/posts")
    public List<PostResponseDto> findPosts() {
        List<PostResponseDto> responseDtoList = postService.findAllPosts();
        return responseDtoList;
    }

    //특정 게시글 보기
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDto> findPost(@PathVariable Long postId) {
        PostResponseDto post = postService.findPostByPostId(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //[1일 1자랑] 강아지 게시글 가지고오기
    @GetMapping("/posts/popular/{type}")
    public ResponseEntity<PostResponseDto> findPopularPost(@PathVariable Long type) {
        PostResponseDto post = postService.findBestPostByDate(type);
        PostResponseDto responseDto = postService.findBestPostByDate(type);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    //게시글 제목으로 postId찾기
    @PostMapping("/posts/findid")
    public ResponseEntity<String> findPostIdByTitle(@RequestBody String title) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject value = (JSONObject) jsonParser.parse(title);
        Long postId = postService.findPostIdByTitle((String) value.get("title"));
        String json = "{\"postId\" : " + postId + "}";
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}