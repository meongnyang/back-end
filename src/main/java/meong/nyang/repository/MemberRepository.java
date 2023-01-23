package meong.nyang.repository;

import meong.nyang.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findMemberByEmail(String email);

    Member findByEmail(String email);
    Member findMemberById(Long id);
    Optional<Member> findMemberByNickname(String nickname);


    //email을 기준으로 User정보를 가져올 때 권한 정보도 같이 가져옴
    @EntityGraph(attributePaths = "authorities")
    Optional<Member> findOneWithAuthoritiesByEmail(String email);

}