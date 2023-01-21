package meong.nyang.dto;

import lombok.Data;

@Data
public class ResponseLoginMemberDto {
    private String token;
    private Long memberId;
    private String email;
    private String nickname;

    public ResponseLoginMemberDto(String token, Long memberId,String nickname, String email) {
        this.token = token;
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
    }
}
