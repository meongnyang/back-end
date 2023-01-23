package meong.nyang.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recordId")
    private Long id;

    @NotNull
    private Long meal;

    @NotNull
    private Long voiding;

    private String voiding_reason;

    @NotNull
    private Long excretion;

    private String excretion_reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="conimalId")
    private Conimal conimal;

    public void update(Long meal, Long voiding, String voiding_reason, String excretion_reason, Long excretion) {
        this.meal = meal;
        this.voiding = voiding;
        this.voiding_reason = voiding_reason;
        this.excretion = excretion;
        this.excretion_reason = excretion_reason;
    }

}
