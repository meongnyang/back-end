package meong.nyang.repository;

import meong.nyang.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Member findMemberByEmail(String email);
    Member findMemberById(Long id);
    Optional<Member> findMemberByNickname(String nickname);
}