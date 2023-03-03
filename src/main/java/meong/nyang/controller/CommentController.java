package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.CommentRequestDto;
import meong.nyang.dto.CommentResponseDto;
import meong.nyang.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    //댓글 작성
    @PostMapping("/comments/{memberId}/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long memberId, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) throws Exception {
        //올바르지 않은 memberId로 댓글을 작성하려고 할 때 에러
        //올바르지 않은 postId로 댓글을 작성하려고 할 때 에러
        try {
            Long commentId = commentService.createComment(commentRequestDto, memberId, postId);
            CommentResponseDto responseDto = commentService.findCommentsByCommentsId(commentId);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) throws Exception{
        //본인이 아닌 댓글을 수정하려고 할 때 에러
        try {
            Long updateCommentId = commentService.updateComment(commentRequestDto, commentId);
            CommentResponseDto responseDto = commentService.findCommentsByCommentsId(updateCommentId);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) throws Exception{
        //본인 댓글이 아닌 댓글을 삭제하려고할 때 에러
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //특정 게시글에 달린 모든 댓글
    @GetMapping("/comments/{postId}")
    public List<CommentResponseDto> findComments(@PathVariable Long postId) {
        List<CommentResponseDto> responseDtoList = commentService.findCommentsByPostId(postId);
        return responseDtoList;
    }
}