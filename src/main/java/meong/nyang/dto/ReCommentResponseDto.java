package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Comment;

@Getter
public class ReCommentResponseDto {
    private Long commentId;
    private String contents;
    private Long memberId;
    private Long postId;
    private String nickname;

    public ReCommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.memberId = comment.getMember().getId();
        this.contents = comment.getContents();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getMember().getNickname();
    }
}
