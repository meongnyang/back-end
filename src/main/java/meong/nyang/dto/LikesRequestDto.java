package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRequestDto {
    private Long memberId;
    private Long postId;

    @Builder
    public LikesRequestDto(Long memberId, Long postId) {
        this.memberId = memberId;
        this.postId = postId;
    }
}
