package meong.nyang.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.MyPageResponseDto;
import meong.nyang.service.MyPageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class MyPageController {
    private final MyPageService mypageService;

    @GetMapping("/mypage/{memberId}")
    public MyPageResponseDto getInfo(@PathVariable Long memberId) {
        return mypageService.getInfo(memberId);
    }
}
