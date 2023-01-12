package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Comment;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;
import meong.nyang.dto.CommentRequestDto;
import meong.nyang.dto.CommentResponseDto;
import meong.nyang.repository.CommentRepository;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    //댓글 생성
    @Transactional
    public Long createComment(CommentRequestDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).get();
        Member member = memberRepository.findById(commentDto.getMemberId()).get();
        Comment comment = commentRepository.save(Comment.toEntity(commentDto.getContents(), member, post));
        return comment.getId();
    }

    //댓글 수정
    @Transactional
    public Long updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findCommentById(commentId);
        comment.update(commentRequestDto.getContents());
        return comment.getId();
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    //댓글 모두 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAllComments() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Comment> list = commentRepository.findAll(sort);
        return list.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    //특정 게시글에 연관된 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentsByPostId(Long postId) {
        List<Comment> list = commentRepository.findCommentsByPostId(postId);
        return list.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    //특정 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findCommentsByCommentsId(Long commentsId) {
        Comment comment = commentRepository.findCommentById(commentsId);
        return new CommentResponseDto(comment);
    }

}