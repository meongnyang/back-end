package meong.nyang.dto;

import lombok.*;
import meong.nyang.domain.Member;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRequestDto {
    private String password;
    private String email;
    private String nickname;
    private String img;
    private String roles;
    private Set<AuthorityDto> authorityDtoSet;
    private Long reportCount;
    public MemberRequestDto(String password, String email, String nickname, String img, String roles, Long reportCount) {
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.img = img;
        this.roles = roles;
        this.reportCount = reportCount;
    }


    public static MemberRequestDto from(Member member) {
        if (member == null) return null;

        return MemberRequestDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .authorityDtoSet(member.getAuthorities().stream()
                        .map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
                        .collect(Collectors.toSet()))
                .reportCount(member.getReportCount())
                .build();
    }
}
