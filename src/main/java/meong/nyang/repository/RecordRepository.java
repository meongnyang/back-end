package meong.nyang.repository;

import meong.nyang.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    Optional<Record> findRecordByCreatedDateAndMemberId(String currentDate, Long memberId);
}
