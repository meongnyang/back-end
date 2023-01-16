package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
