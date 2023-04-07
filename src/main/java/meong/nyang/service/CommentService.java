package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Comment;
import meong.nyang.domain.Member;
import meong.nyang.domain.Post;
import meong.nyang.dto.CommentRequestDto;
import meong.nyang.dto.CommentResponseDto;
import meong.nyang.dto.ReCommentRequestDto;
import meong.nyang.dto.ReCommentResponseDto;
import meong.nyang.exception.CustomException;
import meong.nyang.repository.CommentRepository;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static meong.nyang.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    //댓글 생성
    @Transactional
    public Long createComment(CommentRequestDto commentDto, Long memberId, Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findPost.isEmpty()) {
            throw new CustomException(POST_NOT_FOUND);
        } else if (findMember.isEmpty()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        } else {
            Post post = postRepository.findById(postId).get();
            Member member = memberRepository.findById(memberId).get();
            Comment comment = commentRepository.save(CommentRequestDto.toEntity(commentDto.getContents(), member, post));
            return comment.getId();
        }
    }

    // 대댓글 생성
    @Transactional
    public Long createReComment(Long postId, Long parentId, Long memberId, ReCommentRequestDto commentRequestDto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        Optional<Comment> findComment = commentRepository.findById(parentId);
        Optional<Post> findPost = postRepository.findById(postId);
        if (findMember.isEmpty()) {
            throw new CustomException(MEMBER_NOT_FOUND);
        } else if (findPost.isEmpty()) {
            throw new CustomException(POST_NOT_FOUND);
        } else if (findComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        } else {
            Post post = postRepository.findById(postId).get();
            Member member = memberRepository.findById(memberId).get();
            Comment parent = commentRepository.findCommentById(parentId);
            Comment reComment = commentRepository.save(ReCommentRequestDto.toEntity(commentRequestDto.getContents(), member, post, parent));
            reComment.updateReComment(true);
            return reComment.getId();
        }
    }

    //댓글 수정
    @Transactional
    public Long updateComment(CommentRequestDto commentRequestDto, Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        } else {
            Comment comment = commentRepository.findCommentById(commentId);
            comment.update(commentRequestDto.getContents());
            return comment.getId();
        }
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        } else {
            commentRepository.deleteById(commentId);
        }
    }

    //특정 게시글에 연관된 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentsByPostId(Long postId){
        List<Comment> list = commentRepository.findCommentsByPostId(postId);
        List<Comment> dtoList = new ArrayList<>();
        for(Comment comment : list) {
            if (!comment.isReComment()) {
                dtoList.add(comment);
            }
        }
        return dtoList.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    //특정 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto findCommentsByCommentsId(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        } else {
            Comment comment = commentRepository.findCommentById(commentId);
            return new CommentResponseDto(comment);
        }
    }

    //특정 댓글 조회
    @Transactional(readOnly = true)
    public ReCommentResponseDto findReCommentsByCommentsId(Long commentId) {
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (findComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        } else {
            Comment comment = commentRepository.findCommentById(commentId);
            return new ReCommentResponseDto(comment);
        }
    }

    //댓글 내용으로 댓글Id찾기
    @Transactional
    public Long findCommentIdByContents(String contents) {
        Optional<Comment> findComment = Optional.ofNullable(commentRepository.findCommentByContents(contents));
        if (findComment.isEmpty()) {
            throw new CustomException(COMMENT_NOT_FOUND);
        } else {
            Comment comment = commentRepository.findCommentByContents(contents);
            return comment.getId();
        }
    }

}