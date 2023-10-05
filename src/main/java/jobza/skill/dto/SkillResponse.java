package jobza.skill.dto;

import jobza.skill.entity.Skill;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class SkillResponse {
    private String skillName;

    public static SkillResponse from(Skill skill) {
        return new SkillResponse(skill.getName());
    }
}
