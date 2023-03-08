package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Comment;

import java.util.List;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String contents;
    private Long memberId;
    private Long postId;
    private String nickname;
    private List<Comment> commentList;

    private boolean isReComment;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.memberId = comment.getMember().getId();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getMember().getNickname();
        this.commentList = comment.getChildList();
        this.isReComment = comment.isReComment();
    }

}