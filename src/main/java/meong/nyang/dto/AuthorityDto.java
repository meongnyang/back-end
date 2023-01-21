package meong.nyang.dto;

import lombok.*;

import javax.persistence.Table;

@Getter
@Table(name="authority")
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {
    private String authorityName;
}
