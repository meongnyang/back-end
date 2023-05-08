package meong.nyang.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private Long memberId;
    private String email;
    private String nickname;


    public LoginResponseDto(String token, String nickname, Long memberId, String email) {
        this.token = token;
        this.memberId = memberId;
        this.nickname = nickname;
        this.email = email;
    }
}
