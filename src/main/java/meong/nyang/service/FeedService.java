package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Efficacy;
import meong.nyang.domain.Feed;
import meong.nyang.domain.FeedEfficacy;
import meong.nyang.dto.EfficacyResponseDto;
import meong.nyang.dto.FeedResponseDto;
import meong.nyang.repository.EfficacyRepository;
import meong.nyang.repository.FeedEfficactRepository;
import meong.nyang.repository.FeedRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedEfficactRepository feedEfficactRepository;
    private final EfficacyRepository efficacyRepository;
    //사료 정보 모두 가져오기
    public List<FeedResponseDto> findAllFeed() {
        List<Feed> list = feedRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<FeedResponseDto> dtoList = new ArrayList<>();
        for (Feed feed : list) {
            List<FeedEfficacy> feedEfficacyList = feedEfficactRepository.findAllByFeedId(feed.getId());
            List<Efficacy> efficacyArrayList = new ArrayList<>();
            for (FeedEfficacy feedEfficacy: feedEfficacyList) {
                Efficacy efficacy = efficacyRepository.findById(feedEfficacy.getEfficacy().getId()).get();
                efficacyArrayList.add(efficacy);
            }
            FeedResponseDto feedResponseDto = new FeedResponseDto(feed, efficacyArrayList);
            dtoList.add(feedResponseDto);
        }
        return dtoList;
    }
    //종별로 사료 정보 모두 가져오기
    public List<FeedResponseDto> findAllFeedByType(Long type) {
        List<Feed> list = feedRepository.findAllByType(type);
        List<FeedResponseDto> dtoList = new ArrayList<>();
        for (Feed feed : list) {
            List<FeedEfficacy> feedEfficacyList = feedEfficactRepository.findAllByFeedId(feed.getId());
            List<Efficacy> efficacyArrayList = new ArrayList<>();
            for (FeedEfficacy feedEfficacy: feedEfficacyList) {
                Efficacy efficacy = efficacyRepository.findById(feedEfficacy.getEfficacy().getId()).get();
                efficacyArrayList.add(efficacy);
            }
            FeedResponseDto feedResponseDto = new FeedResponseDto(feed, efficacyArrayList);
            dtoList.add(feedResponseDto);
        }
        return dtoList;
    }
    //특정 사료 정보 가져오기
    public FeedResponseDto findFeedByFeedId(Long feedId) throws Exception{
        Optional<Feed> findFeed = feedRepository.findById(feedId);
        if (findFeed.isEmpty()) {
            throw new Exception("사료 정보가 존재하지 않습니다.");
        } else {
            Feed feed = feedRepository.findById(feedId).get();
            List<FeedEfficacy> feedEfficacyList = feedEfficactRepository.findAllByFeedId(feedId);
            List<Efficacy> efficacyList = new ArrayList<>();
            for (FeedEfficacy feedEfficacy: feedEfficacyList) {
                efficacyList.add(efficacyRepository.findById(feedEfficacy.getEfficacy().getId()).get());
            }
            return new FeedResponseDto(feed, efficacyList);
        }
    }
    //특정 효능을 가진 사료 정보 모두 가져오기
    public List<FeedResponseDto> findFeedByEfficacy(Long efficacyId) throws Exception {
        Optional<Efficacy> findEfficacy = efficacyRepository.findById(efficacyId);
        if (findEfficacy.isEmpty()) {
            throw new Exception("사료 정보가 존재하지 않습니다.");
        } else {
            List<FeedResponseDto> dtoList = new ArrayList<>();
            List<FeedEfficacy> feedEfficacyList = feedEfficactRepository.findAllByEfficacyId(efficacyId);
            for (FeedEfficacy feedEfficacy: feedEfficacyList) {
                Feed feed = feedEfficacy.getFeed();
                List<FeedEfficacy> feedEfficacies = feedEfficactRepository.findAllByFeedId(feed.getId());
                List<Efficacy> efficacyList = new ArrayList<>();
                for (FeedEfficacy feedEfficacy1: feedEfficacies) {
                    efficacyList.add(efficacyRepository.findById(feedEfficacy1.getEfficacy().getId()).get());
                }
                FeedResponseDto feedResponseDto = new FeedResponseDto(feed, efficacyList);
                dtoList.add(feedResponseDto);
            }
            return dtoList;
        }
    }
    //모든 효능 정보 가져오기
    public List<EfficacyResponseDto> findAllEfficacy() {
        List<Efficacy> list = efficacyRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return list.stream().map(EfficacyResponseDto::new).collect(Collectors.toList());
    }
}