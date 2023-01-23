package meong.nyang.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    @CreatedDate
    private String createdDate;

    @Column(name = "created_time", nullable = false, unique = false)
    @CreatedDate
    private String createdTime;

    @PrePersist
    public void onPrePersist() {
        this.createdDate = LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.createdTime = LocalDateTime.now().plusHours(9).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}