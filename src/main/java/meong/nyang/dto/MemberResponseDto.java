package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import meong.nyang.domain.Member;

@Getter @Setter
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String password;

    @Builder
    public MemberResponseDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.password = member.getPassword();
    }

}
