package meong.nyang.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import meong.nyang.domain.Member;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String nickname;
    private String img;
    private String password;

    public static MemberDto of (Member m) {
        return new MemberDto(
                m.getId(),
                m.getEmail(),
                m.getNickname(),
                m.getImg(),
                m.getPassword()
        );
    }
}
