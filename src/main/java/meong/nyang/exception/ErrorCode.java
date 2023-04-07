package meong.nyang.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    //member 관련
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member-001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_MEMBER(HttpStatus.CONFLICT, "Member-002", "존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "Member-003", "존재하는 닉네임입니다."),

    //conimal 관련
    CONIMAL_NOT_FOUND(HttpStatus.NOT_FOUND, "Conimal-001", "반려동물 정보를 찾을 수 없습니다."),
    SPECIES_NOT_FOUND(HttpStatus.NOT_FOUND, "Conimal-002", "종 정보를 찾을 수 없습니다."),
    FEED_NOT_FOUND(HttpStatus.NOT_FOUND, "Conimal-003", "사료 정보를 찾을 수 없습니다."),

    //post 관련
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post-001", "게시글 정보를 찾을 수 없습니다."),
    TODAY_POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post-002", "오늘 게시글이 없습니다."),

    //comment 관련
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Comment-001", "댓글 정보를 찾을 수 없습니다."),
    NOT_YOUR_COMMENT(HttpStatus.BAD_REQUEST, "Comment-002", "본인 댓글이 아닙니다."),

    //record 관련
    NOT_YOUR_CONIMAL(HttpStatus.BAD_REQUEST, "Record-001", "본인 반려동물이 아닙니다."),
    DUPLICATE_RECORD(HttpStatus.CONFLICT, "Record-002", "오늘의 건강기록을 이미 등록했습니다.")
    ;

    private final HttpStatus httpStatus;    // HttpStatus
    private final String code;                // ACCOUNT-001
    private final String message;            // 설명
}
