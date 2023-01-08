package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Record {
    @Id
    @GeneratedValue
    @Column(name = "recordId")
    private Long id;

    @NotNull
    private Date date;

    @NotNull
    private Long meal;

    @NotNull
    private Long voiding;

    private String reason1;

    @NotNull
    private Long excretion;

    private String reason2;

    private String symptom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="conimalId")
    private Conimal conimal;
}
