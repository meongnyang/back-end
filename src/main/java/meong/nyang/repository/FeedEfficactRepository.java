package meong.nyang.repository;

import meong.nyang.domain.FeedEfficacy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedEfficactRepository extends JpaRepository<FeedEfficacy, Long> {
    List<FeedEfficacy> findAllByFeedId(Long feedId);
    List<FeedEfficacy> findAllByEfficacyId(Long efficacyId);
}