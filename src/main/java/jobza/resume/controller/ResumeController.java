package jobza.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.resume.dto.ResumeRequest;
import jobza.resume.entity.Resume;
import jobza.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary = "이력서 업로드", description = "이력서 pdf 업로드")
    @PostMapping( value = "/resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> uploadResume(@ModelAttribute ResumeRequest resumeRequest) {
        resumeService.uploadResume(resumeRequest.getResume());
        return ResponseEntity.ok(new Response("이력서 업로드 완료"));
    }
}
