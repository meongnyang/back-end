package meong.nyang.repository;

import meong.nyang.domain.Conimal;
import meong.nyang.dto.MyPageConimalResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConimalRepository extends JpaRepository<Conimal, Long> {
    List<Conimal> findConimalsByMemberId(Long memberId);
    Conimal findConimalsById(Long id);

}
