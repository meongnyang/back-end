package meong.nyang.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String email;
    private String password;
}
