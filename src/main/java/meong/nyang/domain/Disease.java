package meong.nyang.domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
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
