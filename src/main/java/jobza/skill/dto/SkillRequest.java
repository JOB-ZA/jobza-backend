package jobza.skill.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SkillRequest {
    @Schema(description = "기술 스택 리스트", example = "[\"SPRING\", \"DOCKER\"]")
    private List<String> skillList;
}
