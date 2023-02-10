package meong.nyang.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import meong.nyang.dto.EfficacyResponseDto;
import meong.nyang.dto.FeedResponseDto;
import meong.nyang.service.FeedService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;
    //사료 정보 모두 가져오기
    @GetMapping("/feed")
    public List<FeedResponseDto> findAllFeed() {
        List<FeedResponseDto> responseDtoList = feedService.findAllFeed();
        return responseDtoList;
    }
    //종별로 사료 정보 모두 가져오기
    @GetMapping("/feed/type/{type}")
    public List<FeedResponseDto> findFeedByType(@PathVariable Long type) {
        List<FeedResponseDto> responseDtoList = feedService.findAllFeedByType(type);
        return responseDtoList;
    }
    //특정 사료 정보 가져오기
    @GetMapping("/feed/{feedId}")
    public ResponseEntity<FeedResponseDto> findFeedByFeedId(@PathVariable Long feedId) {
        try {
            FeedResponseDto feed = feedService.findFeedByFeedId(feedId);
            return new ResponseEntity<>(feed, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //특정 효능을 가진 사료 정보 모두 가져오기
    @GetMapping("/feed/efficacy/{efficacyId}")
    public ResponseEntity<List<FeedResponseDto>> findFeedByEfficacyId(@PathVariable Long efficacyId) {
        try {
            List<FeedResponseDto> responseDtoList = feedService.findFeedByEfficacy(efficacyId);
            return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //모든 효능 정보 가져오기
    @GetMapping("/efficacy")
    public List<EfficacyResponseDto> findAllEfficacy() {
        List<EfficacyResponseDto> responseDtoList = feedService.findAllEfficacy();
        return responseDtoList;
    }
}