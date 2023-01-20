package meong.nyang.dto;

import meong.nyang.domain.Likes;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;

public class LikesRequestDto {
    public static Likes toEntity(Member member, Post post) {
        return Likes.builder()
                .member(member)
                .post(post)
                .build();
    }
}
