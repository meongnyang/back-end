package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Comment;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;
import meong.nyang.dto.CommentRequestDto;
import meong.nyang.dto.CommentResponseDto;
import meong.nyang.dto.ReCommentRequestDto;
import meong.nyang.dto.ReCommentResponseDto;
import meong.nyang.repository.CommentRepository;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    //댓글 생성
    @Transactional
    public Long createComment(CommentRequestDto commentDto, Long memberId, Long postId) throws Exception{
        Optional<Post> findPost = postRepository.findById(postId);
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findPost.isEmpty() && findMember.isEmpty()) {
            throw new Exception("회원 정보와 게시글 정보가 존재하지 않습니다.");
        } else if (findPost.isEmpty()) {
            throw new Exception("게시글 정보가 존재하지 않습니다.");
        } else if (findMember.isEmpty()) {
            throw new Exception("회원 정보가 존재하지 않습니다.");
        } else {
            Post post = postRepository.findById(postId).get();
            Member member = memberRepository.findById(memberId).get();
            Comment comment = commentRepository.save(CommentRequestDto.toEntity(commentDto.getContents(), member, post));

            //comment.updateReComment(false);
            return comment.getId();
        }
    }

    // 대댓글 생성
    @Transactional
    public Long createReComment(Long postId, Long parentId, Long memberId, ReCommentRequestDto commentRequestDto) throws Exception {
        Optional<Comment> findComment = commentRepository.findById(parentId);
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty() && findComment.isEmpty()) {
            throw new Exception("해당 댓글과 게시글 정보가 존재하지 않습니다.");
        } else if (findPost.isEmpty()) {
            throw new Exception("게시글 정보가 존재하지 않습니다.");
        } else if (findComment.isEmpty()) {
            throw new Exception("해당 댓글이 존재하지 않습니다.");
        } else {
            Post post = postRepository.findById(postId).get();
            Member member = memberRepository.findById(memberId).get();
            //Comment parent = commentRepository.findCommentById(parentId);
            Comment parent = commentRepository.findCommentById(parentId);
            Comment reComment = commentRepository.save(ReCommentRequestDto.toEntity(commentRequestDto.getContents(), member, post, parent));

            reComment.updateReComment(true);
            return reComment.getId();
        }
    }


    //댓글 수정
    @Transactional
    public Long updateComment(CommentRequestDto commentRequestDto, Long commentId) throws Exception{
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new Exception("댓글 정보가 존재하지 않습니다.");
        } else {
            Comment comment = commentRepository.findCommentById(commentId);
            comment.update(commentRequestDto.getContents());
            return comment.getId();
        }
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) throws Exception{
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new Exception("댓글 정보가 존재하지 않습니다.");
        } else {
            commentRepository.deleteById(commentId);
        }
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
    public List<CommentResponseDto> findCommentsByPostId(Long postId){
        List<Comment> list = commentRepository.findCommentsByPostId(postId);
        return list.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    //특정 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findCommentsByCommentsId(Long commentId) throws Exception {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new Exception("댓글 정보가 존재하지 않습니다.");
        } else {
            Comment comment = commentRepository.findCommentById(commentId);
            return new CommentResponseDto(comment);
        }
    }

    //특정 댓글 조회
    @Transactional(readOnly = true)
    public ReCommentResponseDto findReCommentsByCommentsId(Long commentId) throws Exception {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new Exception("댓글 정보가 존재하지 않습니다.");
        } else {
            Comment comment = commentRepository.findCommentById(commentId);
            return new ReCommentResponseDto(comment);
        }
    }

}