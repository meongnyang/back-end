package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diseaseId")
    private Long id;
    @NotNull
    private Long type;
    @NotNull
    private String name;
    @Column(columnDefinition = "TEXT", length = 65535)
    private String reason;
    @Column(columnDefinition = "TEXT", length = 65535)
    private String manage;

}
