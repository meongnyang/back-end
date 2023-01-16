package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.CommentRequestDto;
import meong.nyang.dto.CommentResponseDto;
import meong.nyang.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    //댓글 작성
    @PostMapping("/comments/{memberId}/{postId}")
    public CommentResponseDto createComment(@PathVariable Long memberId, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        Long commentId = commentService.createComment(commentRequestDto, memberId, postId);
        CommentResponseDto responseDto = commentService.findCommentsByCommentsId(commentId);
        return responseDto;
    }
    //댓글 수정
    @PutMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        Long updateCommentId = commentService.updateComment(commentRequestDto, commentId);
        CommentResponseDto responseDto = commentService.findCommentsByCommentsId(updateCommentId);
        return responseDto;
    }
    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
    //특정 게시글에 달린 모든 댓글
    @GetMapping("/comments/{postId}")
    public List<CommentResponseDto> findComments(@PathVariable Long postId) {
        List<CommentResponseDto> responseDtoList = commentService.findCommentsByPostId(postId);
        return responseDtoList;
    }
}