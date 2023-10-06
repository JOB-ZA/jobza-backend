package jobza.skill.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.security.principal.PrincipalDetails;
import jobza.skill.dto.SkillRequest;
import jobza.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SkillController {
    private final SkillService skillService;

    @Operation(summary = "skill 저장 되는지 테스트 엔드포인트", description = "나중에 지울 예정")
    @PostMapping("/skill")
    public ResponseEntity<Response> saveSkills(@RequestBody SkillRequest skillRequest,
                                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        skillService.saveSkill(principalDetails.getMember().getId(), skillRequest.getSkillList());
        return ResponseEntity.ok(new Response("스킬 저장 완료"));
    }
}
