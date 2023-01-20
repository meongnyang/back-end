package meong.nyang.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String password;
    private String email;
    private String nickname;

    private String img;


    public MemberRequestDto(String password, String email, String nickname, String img) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.img = img;
    }

}
