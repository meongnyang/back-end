package meong.nyang.dto;

import lombok.*;
import meong.nyang.domain.Member;

import java.util.Set;
import java.util.stream.Collectors;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {
    private Long id;
    private String password;
    private String email;
    private String nickname;
    private String img;

    private Set<AuthorityDto> authorityDtoSet;

    public MemberRequestDto(String password, String email, String nickname, String img, Long id) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.img = img;
    }


    public static MemberRequestDto from(Member member) {
        if(member == null) return null;

        return MemberRequestDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .build();
    }

}
