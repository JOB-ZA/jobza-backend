package jobza.resume.controller;

import com.opencsv.exceptions.CsvException;
import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.resume.service.ResumeAnalyzer;
import jobza.resume.dto.JobRecommendResponse;
import jobza.resume.dto.ResumeRequest;
import jobza.resume.entity.Resume;
import jobza.resume.service.ResumeService;
import jobza.resume.service.TestService;
import jobza.security.principal.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeAnalyzer resumeAnalyzer;
    private final TestService testService;

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

    @Operation(summary = "회원에 저장된 pdf 로 직업 추천")
    @GetMapping("/recommend-job/resume")
    public ResponseEntity<Response> recommendJobWithResume(@AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException, CsvException {
        InputStream pdf = resumeService.getPdfFromS3(principalDetails.getMember().getId());
        List<JobRecommendResponse> jobRecommendResponseList = resumeAnalyzer.recommendJobWithResume(pdf);
        return ResponseEntity.ok(new Response(jobRecommendResponseList, "이력서 기반 추천된 직업 리스트 반환"));
    }
    @Operation(summary = "회원에 저장된 pdf 로 직업 추천 - ThreadPoolTaskExecutor")
    @GetMapping("/recommend-job/resume/v2")
    public ResponseEntity<Response> recommendJobWithResumeV2(@AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException, CsvException {
        InputStream pdf = resumeService.getPdfFromS3(principalDetails.getMember().getId());
        CompletableFuture<List<JobRecommendResponse>> listCompletableFuture = resumeAnalyzer.recommendJobWithResumeWithMultiThread(pdf);
        return ResponseEntity.ok(new Response(listCompletableFuture.join(), "이력서 기반 추천된 직업 리스트 반환"));
    }

    @Operation(summary = "회원에 저장된 pdf 로 직업 추천 - Async 사용")
    @GetMapping("/recommend-job/resume/v3")
    public ResponseEntity<Response> recommendJobWithResumeV3(@AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException, CsvException {
        InputStream pdf = resumeService.getPdfFromS3(principalDetails.getMember().getId());
        List<JobRecommendResponse> jobRecommendResponses = resumeAnalyzer.recommendJobWithResumeV2(pdf);

        return ResponseEntity.ok(new Response(jobRecommendResponses, "이력서 기반 추천된 직업 리스트 반환"));
    }

    @Operation(summary = "회원에 저장된 pdf 로 직업 추천 - 아무것도 사용 x")
    @GetMapping("/recommend-job/resume/v4")
    public ResponseEntity<Response> recommendJobWithResumeV4(@AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException, CsvException {
        long startTime = System.currentTimeMillis(); // 시작 시간 기록
        InputStream pdf = resumeService.getPdfFromS3(principalDetails.getMember().getId());
        List<JobRecommendResponse> jobRecommendResponses = resumeAnalyzer.recommendJobWithResumeV3(pdf);

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산
        System.out.println("recommendJobWithResumeV4 실행 시간: " + duration + "ms");
        return ResponseEntity.ok(new Response(jobRecommendResponses, "이력서 기반 추천된 직업 리스트 반환"));
    }

    @GetMapping("/test/test")
    public ResponseEntity<Response> testtest() throws IOException, CsvException {
        long startTime = System.currentTimeMillis(); // 시작 시간 기록
        for(int i=0;i<10;i++) {
            testService.testtest(i);
        }
        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산
        System.out.println("recommendJobWithResumeV4 실행 시간: " + duration + "ms");
        return ResponseEntity.ok(new Response(null, "이력서 기반 추천된 직업 리스트 반환"));
    }

    @Operation(summary = "회원에 저장된 스킬로 직업 추천")
    @GetMapping("/recommend-job/skill")
    public ResponseEntity<Response> recommendJobWithSkill(@AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException, CsvException {
        List<JobRecommendResponse> jobRecommendResponseList = resumeAnalyzer.recommendJobWithSkill(principalDetails.getMember().getId());
        return ResponseEntity.ok(new Response(jobRecommendResponseList, "설정한 기술 스택 기반 추천된 직업 리스트 반환"));
    }

}
