package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import meong.nyang.domain.Post;

@Getter
public class PostResponseDto {
    private Long postId;
    private Long category;
    private Long type;
    private String title;
    private String contents;
    private Long count;
    private String img;
    private Long memberId;

    @Builder
    public PostResponseDto(Post post){
        this.postId = post.getId();
        this.category = post.getCategory();
        this.type = post.getType();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.count = post.getCount();
        this.img = post.getImg();
        this.memberId = post.getMember().getId();
    }
}
