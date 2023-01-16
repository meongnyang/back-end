package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import meong.nyang.domain.Post;

import java.util.HashMap;
import java.util.Map;

@Getter
public class PostResponseDto {
    private Long postId;
    private Long category;
    private String categoryName;
    private Long type;
    private String title;
    private String contents;
    private Long count;
    private String img;
    private Long memberId;
    private String date;
    private String time;

    @Builder
    public PostResponseDto(Post post){
        Map<Long, String> dict = new HashMap<>(){{
            put(1L, "자유");
            put(2L, "질문");
            put(3L, "1일 1자랑");
        }};
        this.memberId = post.getMember().getId();
        this.postId = post.getId();
        this.category = post.getCategory();
        this.categoryName = dict.get(post.getCategory());
        this.type = post.getType();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.count = post.getCount();
        this.img = post.getImg();
        this.date = post.getCreatedDate();
        this.time = post.getCreatedTime();
    }
}
