package meong.nyang.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reportId")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @NotNull
    @Column(nullable = false)
    private Long attackerId;

    @Column(columnDefinition = "TEXT", length = 65535)
    private String contents;

    @Builder
    public Report(Long reportId, Member member, Long attackerId, String contents) {
        this.reportId = reportId;
        this.member = member;
        this.attackerId = attackerId;
        this.contents = contents;
    }
}
