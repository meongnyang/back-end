package meong.nyang.service;

import lombok.RequiredArgsConstructor;
import meong.nyang.domain.Conimal;
import meong.nyang.domain.Member;
import meong.nyang.domain.Species;
import meong.nyang.dto.ConimalRequestDto;
import meong.nyang.dto.ConimalResponseDto;
import meong.nyang.repository.ConimalRepository;
import meong.nyang.repository.MemberRepository;
import meong.nyang.repository.SpeciesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConimalService {
    private final MemberRepository memberRepository;
    private final ConimalRepository conimalRepository;
    private final SpeciesRepository speciesRepository;

    //반려동물 정보 등록
    @Transactional
    public Long createConimal(ConimalRequestDto conimalRequestDto, Long memberId) throws Exception{
        Optional<Member> findMember = memberRepository.findById(memberId);
        Optional<Species> findSpecies = Optional.ofNullable(speciesRepository.findSpeciesByName(conimalRequestDto.getSpeciesName()));
        if (findMember.isEmpty()) {
            throw new Exception("회원이 존재하지 않습니다.");
        } else if (findSpecies.isEmpty()) {
            throw new Exception("종 정보가 존재하지 않습니다.");
        } else {
            Member member = memberRepository.findMemberById(memberId);
            Species species = speciesRepository.findSpeciesByName(conimalRequestDto.getSpeciesName());
            Conimal conimal = conimalRepository.save(ConimalRequestDto.toEntity(conimalRequestDto.getType(), conimalRequestDto.getName(),
                    conimalRequestDto.getGender(), conimalRequestDto.getNeutering(), conimalRequestDto.getBirth(),
                    conimalRequestDto.getAdopt(),member, species));
            return conimal.getId();
        }
    }
    //반려동물 정보 수정
    @Transactional
    public Long updateConimal(ConimalRequestDto conimalRequestDto, Long conimalId) throws Exception{
        Optional<Conimal> findConimal = conimalRepository.findById(conimalId);
        if (findConimal.isEmpty()) {
            throw new Exception("반려동물 정보가 존재하지 않습니다.");
        } else {
            Conimal conimal = conimalRepository.findById(conimalId).get();
            ConimalRequestDto dto = conimalRequestDto;
            Species species = speciesRepository.findSpeciesByName(dto.getSpeciesName());
            if (dto.getName() != null) conimal.updateName(dto.getName());
            if (dto.getType() != null) conimal.updateType(dto.getType());
            if (dto.getGender() != null) conimal.updateGender(dto.getGender());
            if (dto.getNeutering() != null) conimal.updateNeutering(dto.getNeutering());
            if (dto.getBirth() != null) conimal.updateBirth(dto.getBirth());
            if (dto.getAdopt() != null) conimal.updateAdopt(dto.getAdopt());
            if (dto.getImg() != null) conimal.updateImg(dto.getImg()); else conimal.updateImg("http://localhost/image/image.png");
            if (dto.getSpeciesName() != null) conimal.updateSpecies(species);
            return conimal.getId();
        }
    }
    //반려동물 정보 삭제
    @Transactional
    public void deleteConimal(Long conimalId) throws Exception{
        Optional<Conimal> findConiaml = conimalRepository.findById(conimalId);
        if (findConiaml.isEmpty()) {
            throw new Exception("반려동물 정보가 존재하지 않습니다.");
        } else {
            conimalRepository.deleteById(conimalId);
        }
    }
    //특정 회원의 반려동물 정보 모두 가져오기
    @Transactional(readOnly = true)
    public List<ConimalResponseDto> findConimalsBymemberId(Long memberId) {
        List<Conimal> list = conimalRepository.findConimalsByMemberId(memberId);
        return list.stream().map(ConimalResponseDto::new).collect(Collectors.toList());
    }
    //특정 반려동물 정보 가져오기
    @Transactional(readOnly = true)
    public ConimalResponseDto findConimalById(Long conimalId) throws Exception{
        Optional<Conimal> findConiaml = conimalRepository.findById(conimalId);
        if (findConiaml.isEmpty()) {
            throw new Exception("반려동물 정보가 존재하지 않습니다.");
        } else {
            Conimal conimal = conimalRepository.findConimalsById(conimalId);
            return new ConimalResponseDto(conimal);
        }
    }
}
