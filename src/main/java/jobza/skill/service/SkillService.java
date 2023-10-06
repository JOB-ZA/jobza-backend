package jobza.skill.service;

import jobza.member.entity.Member;
import jobza.member.service.MemberService;
import jobza.skill.dto.SkillResponse;
import jobza.skill.entity.Skill;
import jobza.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SkillService {
    private final SkillRepository skillRepository;
    private final MemberService memberService;

    public void saveSkill(Long memberId, List<String> skillNameList) {
        Member member = memberService.findMemberById(memberId);

        if (skillRepository.existsSkillByMember(memberId)) {
            skillRepository.deleteByMemberId(memberId);
            log.info("기존에 저장된 skill 전체 삭제");
        }

        List<Skill> SkillList = skillNameList.stream()
                .map(skillName -> Skill.builder()
                        .name(skillName)
                        .member(member)
                        .build())
                .collect(Collectors.toList());
        List<Skill> skills = skillRepository.saveAll(SkillList);
        log.info("저장된 스킬 개수: " + skills.size());
    }

    // 회원의 보유 skill 전체 조회
    public List<SkillResponse> findAllSkill(Long memberId) {
        return skillRepository.findByMemberId(memberId).stream()
                .map(skill -> SkillResponse.from(skill))
                .collect(Collectors.toList());
    }
}
