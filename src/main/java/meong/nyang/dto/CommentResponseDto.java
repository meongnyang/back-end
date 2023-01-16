package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Comment;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String contents;
    private Long memberId;
    private Long postId;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.memberId = comment.getMember().getId();
        this.postId = comment.getPost().getId();
    }
}