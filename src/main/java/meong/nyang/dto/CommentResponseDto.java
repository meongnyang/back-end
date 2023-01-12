package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Comment;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String contents;
    private Member member;
    private Post post;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.member = comment.getMember();
        this.post = comment.getPost();
    }
}