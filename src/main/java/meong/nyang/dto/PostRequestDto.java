package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private Long category;
    private Long type;
    private String title;
    private String contents;
    private String img;

    @Builder
    public PostRequestDto(Long category, Long type, String title, String contents, String img){
        this.category = category;
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.img = img;
    }

    public static Post toEntity(Long category, Long type, String title, String contents, String img, Member member){
        return Post.builder()
                .category(category)
                .type(type)
                .title(title)
                .contents(contents)
                .img(img)
                .member(member)
                .build();
    }

}
