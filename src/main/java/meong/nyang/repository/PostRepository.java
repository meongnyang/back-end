package meong.nyang.repository;

import meong.nyang.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findPostsByCreatedDateAndCategory(String currentDate, Long type);
}