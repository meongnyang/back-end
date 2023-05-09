package meong.nyang.repository;

import meong.nyang.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findMemberByEmail(String email);
    Member findMemberById(Long id);
    Member findMemberByNickname(String nickname);

    @EntityGraph(attributePaths = "authorities")
    Optional<Object> findOneWithAuthoritiesByEmail(String email);

    Member findByEmail(String email);
}