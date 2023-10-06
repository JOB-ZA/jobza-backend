package jobza.skill;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jobza.common.response.Response;
import jobza.security.principal.PrincipalDetails;
import jobza.skill.service.SkillService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SkillController {
    private final SkillService skillService;

    @Operation(summary = "skill 저장 되는지 테스트 엔드포인트", description = "나중에 지울 예정")
    @PostMapping("/skill")
    public ResponseEntity<Response> saveSkills(@RequestBody SkillRequest skillRequest,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        skillService.saveSkill(principalDetails.getMember().getId(), skillRequest.getSkillName());
        return ResponseEntity.ok(new Response("스킬 저장 완료"));
    }
    @Data
    @NoArgsConstructor
    public static class SkillRequest {
        @Schema(description = "보유 스킬 리스트", example = "[\"Spring\", \"Docker\"]")
        List<String> skillName;
    }
}
