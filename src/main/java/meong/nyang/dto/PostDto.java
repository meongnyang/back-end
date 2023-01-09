package meong.nyang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import meong.nyang.domain.Post;

import java.util.Date;

@Data
@AllArgsConstructor
public class PostDto {
    private Long memberId;
    private Long postId;
    private Long category;
    private String title;
    private String contents;
    private String date;
    private String img;

    public static PostDto of (Post p) {
        return new PostDto(
                p.getMember().getId(),
                p.getId(),
                p.getCategory(),
                p.getTitle(),
                p.getContents(),
                p.getDate(),
                p.getImg()
        );
    }
}