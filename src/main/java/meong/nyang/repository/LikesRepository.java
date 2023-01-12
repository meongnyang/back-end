package meong.nyang.repository;

import meong.nyang.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findLikesByMemberIdAndPostId(Long memberId, Long postId);
}
