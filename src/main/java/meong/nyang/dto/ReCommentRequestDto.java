package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import meong.nyang.domain.Comment;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;

@Getter
@NoArgsConstructor
public class ReCommentRequestDto {

    private String contents;

    @Builder
    public ReCommentRequestDto(String contents) {
        this.contents = contents;
    }

    public static Comment toEntity(String contents, Member member, Post post, Comment parent) {
        return Comment.builder()
                .contents(contents)
                .member(member)
                .post(post)
                .parent(parent)
                .build();
    }
}
