package meong.nyang.repository;

import meong.nyang.domain.Conimal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConimalRepository extends JpaRepository<Conimal, Long> {
    List<Conimal> findConimalsByMemberId(Long memberId);
}