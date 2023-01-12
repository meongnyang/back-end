package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import meong.nyang.domain.Likes;

@Getter
public class LikesResponseDto {
    private Long id;
    private Long memberId;
    private Long postId;

    @Builder
    public LikesResponseDto(Likes likes) {
        this.id = likes.getId();;
        this.memberId = likes.getMember().getId();
        this.postId = likes.getPost().getId();
    }
}
