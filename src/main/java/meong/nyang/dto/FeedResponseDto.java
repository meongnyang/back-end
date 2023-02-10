package meong.nyang.dto;

import lombok.Getter;
import meong.nyang.domain.Efficacy;
import meong.nyang.domain.Feed;

import java.util.List;

@Getter
public class FeedResponseDto {
    private Long feedId;
    private Long type;
    private String name;
    private String material;
    private String ingredient;
    private String img;
    private List<Efficacy> efficacyList;

    public FeedResponseDto(Feed feed, List<Efficacy> efficacy) {
        this.feedId = feed.getId();
        this.type = feed.getType();
        this.name = feed.getName();
        this.material = feed.getMaterial();
        this.ingredient = feed.getIngredient();
        this.img = feed.getImg();
        this.efficacyList = efficacy;
    }
}