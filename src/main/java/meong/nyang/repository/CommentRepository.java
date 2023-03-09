package meong.nyang.repository;

import meong.nyang.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentById(Long commentId);
    List<Comment> findCommentsByPostId(Long postId);

    Comment findCommentByContents(String contents);
}
