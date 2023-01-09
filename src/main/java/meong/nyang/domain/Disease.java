package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Disease {
    @Id
    @GeneratedValue
    @Column(name = "diseaseId")
    private Long id;

    @NotNull
    private Long type;

    @NotNull
    private String name;

    private String reason;

    private String manage;

}
