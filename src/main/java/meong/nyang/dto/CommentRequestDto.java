package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private String contents;
    private Long memberId;
    private Long postId;

    @Builder
    public CommentRequestDto(String contents, Long memberId, Long postId) {
        this.contents = contents;
        this.memberId = memberId;
        this.postId = postId;
    }
}