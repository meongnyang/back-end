package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.service.LikesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;
    //좋아요 생성, 취소
    @PostMapping("/likes/{memberId}/{postId}")
    public ResponseEntity<String> updateLikes(@PathVariable Long memberId, @PathVariable Long postId) {
        likesService.postLike(memberId, postId);
        Long count = likesService.findByLikesByPost(postId);
        String json = "{\"count\" : " + count + "}";
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}