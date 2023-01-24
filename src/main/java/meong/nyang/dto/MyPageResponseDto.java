package meong.nyang.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Member;
import meong.nyang.domain.Species;

import java.util.Date;
import java.util.List;

@Getter
public class MyPageResponseDto {
        private String nickname;
        private String memberImg;
        private Long memberId;
        private List<MyPageConimalResponseDto> conimals;


        @Builder
        public MyPageResponseDto(Member member, List<MyPageConimalResponseDto> conimals) {

            this.nickname = member.getNickname();
            this.memberId = member.getId();
            this.memberImg = member.getImg();
            this.conimals = conimals;
    }
}
