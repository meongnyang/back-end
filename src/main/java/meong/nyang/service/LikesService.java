package meong.nyang.service;
import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Likes;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;
import meong.nyang.dto.LikesRequestDto;
import meong.nyang.repository.LikesRepository;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    //좋아요(정보가 있으면 삭제, 정보가 없으면 생성)
    @Transactional
    public void postLike(LikesRequestDto likesRequestDto) {
        Member member = memberRepository.findById(likesRequestDto.getMemberId()).get();
        Post post = postRepository.findById(likesRequestDto.getPostId()).get();
        Optional<Likes> likes = likesRepository.findLikesByMemberIdAndPostId(member.getId(), post.getId());
        likes.ifPresentOrElse(
                //좋아요를 이미 누른 경우 삭제
                likes1 -> {
                    likesRepository.deleteById(likes1.getId());
                    post.updateLikes(post.getCount()-1L);
                },
                () -> {
                    //좋아요가 안눌린 경우 좋아요를 누름
                    Likes likes1 = likesRepository.save(Likes.toEntity(member,post));
                    post.updateLikes(post.getCount()+1L);
                }
        );
    }
}