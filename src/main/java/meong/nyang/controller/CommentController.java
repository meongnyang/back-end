package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.*;
import meong.nyang.service.CommentService;
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
public class CommentController {
    private final CommentService commentService;
    //댓글 작성
    @PostMapping("/comments/{memberId}/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long memberId, @PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        Long commentId = commentService.createComment(commentRequestDto, memberId, postId);
        CommentResponseDto responseDto = commentService.findCommentsByCommentsId(commentId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    //대댓글 작성
    @PostMapping("/comments/{memberId}/{postId}/{commentId}")
    public ResponseEntity<ReCommentResponseDto> reCommentSave(@PathVariable("memberId") Long memberId,
                                                              @PathVariable("postId") Long postId,
                                                              @PathVariable("commentId") Long parentId,
                                                              @RequestBody ReCommentRequestDto commentRequestDto) {
        Long commentId = commentService.createReComment(parentId, postId, memberId, commentRequestDto);
        ReCommentResponseDto responseDto = commentService.findReCommentsByCommentsId(commentId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    //댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto) {
        Long updateCommentId = commentService.updateComment(commentRequestDto, commentId);
        CommentResponseDto responseDto = commentService.findCommentsByCommentsId(updateCommentId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //특정 게시글에 달린 모든 댓글
    @GetMapping("/comments/{postId}")
    public List<CommentResponseDto> findComments(@PathVariable Long postId) {
        return commentService.findCommentsByPostId(postId);
    }

    //내용으로 댓글Id 찾기
    @PostMapping("/comments/findId")
    public ResponseEntity<String> findCommentIdByContents(@RequestBody String contents) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject value = (JSONObject) jsonParser.parse(contents);
        Long commentId = commentService.findCommentIdByContents((String) value.get("contents"));
        String json = "{\"commentId\" : " + commentId + "}";
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}