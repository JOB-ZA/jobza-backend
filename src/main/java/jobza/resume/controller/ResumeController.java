package jobza.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.resume.dto.ResumeRequest;
import jobza.resume.entity.Resume;
import jobza.resume.service.ResumeService;
import jobza.security.principal.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary = "이력서 업로드", description = "이력서 pdf 업로드<br>이미 업로드된 이력서가 있다면 지우고 새로 올린 이력서로 저장(업데이트 기능 포함)")
    @PostMapping( value = "/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> uploadResume(@ModelAttribute ResumeRequest resumeRequest,
                                                 @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Optional<Resume> resume = resumeService.alreadyUploadCheck(principalDetails.getMember().getId());
        if (resume.isPresent()) {
            resumeService.deleteResume(resume.get());
        }
        resumeService.uploadResume(resumeRequest.getResume(), principalDetails.getMember().getId());
        return ResponseEntity.ok(new Response("이력서 업로드 완료"));
    }

}
