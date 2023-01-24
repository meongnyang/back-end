package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.MemberResponseDto;
import meong.nyang.dto.PostResponseDto;
import meong.nyang.service.LikesService;
import meong.nyang.service.MemberService;
import meong.nyang.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final MemberService memberService;
    private final PostService postService;
    private final LikesService likesService;
    //좋아요 생성, 취소
    @PostMapping("/likes/{memberId}/{postId}")
    public ResponseEntity<String> updateLikes(@PathVariable Long memberId, @PathVariable Long postId) throws Exception {
        //memberId가 올바르지 않을 때 에러
        //postId가 올바르지 않을 때 에러
        try {
            MemberResponseDto member = memberService.findMemberByMemberId(memberId);
            PostResponseDto post = postService.findPostByPostId(postId);
            likesService.postLike(memberId, postId);
            Long count = likesService.findByLikesByPost(postId);
            String json = "{\"count\" : " + count + "}";
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}