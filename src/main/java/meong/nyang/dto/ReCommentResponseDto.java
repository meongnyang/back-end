package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Comment;

@Getter
public class ReCommentResponseDto {
    private Long commentId;
    private Long parentId;
    private String contents;
    private Long memberId;
    private Long postId;
    private String nickname;

    private boolean isReComment;

    public ReCommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.memberId = comment.getMember().getId();
        this.contents = comment.getContents();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getMember().getNickname();
        this.parentId = comment.getId();
        this.isReComment = true;
    }
}
