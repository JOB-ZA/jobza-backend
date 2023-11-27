package jobza.recommend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import jobza.common.response.Response;
import jobza.recommend.dto.ChartRequest;
import jobza.recommend.dto.ChartResponse;
import jobza.recommend.dto.JobPostResponse;
import jobza.recommend.dto.PreferRequest;
import jobza.recommend.entity.Employment;
import jobza.recommend.service.RecommendService;
import jobza.security.principal.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendService recommendService;

    @Operation(summary = "맞춤 공고 요청", description = "직업 선택 및 편의시설 선호도에 따라 맞춤 공고 데이터 반환")
    @PostMapping("/job-post/prefer")
    public ResponseEntity<Response> jobPostByMemberPrefer(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          @RequestBody PreferRequest request,
                                                          HttpSession session) throws IOException {
        List<JobPostResponse> responseList = recommendService.jobPostByMemberPrefer(request);
        Long memberId = principalDetails.getMember().getId();
        session.setAttribute("request", request);
        return ResponseEntity.ok(new Response(responseList, "맞춤 공고 데이터 반환"));
    }

    @Operation(summary = "공고 상세 데이터 요청", description = "id 값으로 공고 상세 데이터 반환")
    @GetMapping("/job-post/{wantedAuthNo}")
    public ResponseEntity<Response> jobPostById(@PathVariable(name = "wantedAuthNo") String wantedAuthNo) {
        Employment job = recommendService.findByWantedAuthNo(wantedAuthNo);
        return ResponseEntity.ok(new Response(job, "상세 공고 데이터 반환"));
    }

    @Operation(summary = "공고 상세 데이터 요청 with python", description = "id 값으로 공고 상세 데이터 반환")
    @GetMapping("/job-post/python/{wantedAuthNo}")
    public ResponseEntity<Response> jobPostByIdWithPython(@PathVariable(name = "wantedAuthNo") String wantedAuthNo) throws IOException {
        JobPostResponse jobPostResponse = recommendService.findByWantedAuthNoWithPython(wantedAuthNo);
        return ResponseEntity.ok(new Response(jobPostResponse, "상세 공고 데이터 반환"));
    }

    @Operation(summary = "차트 데이터 반환", description = "wantedAuthNo 과 유저가 입력한 가중치를 바탕으로 차트 데이터 반환")
    @PostMapping("/job-post/chart")
    public ResponseEntity<Response> getChartData(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                 @RequestBody ChartRequest request,
                                                 HttpSession session) throws IOException {
        Long memberId = principalDetails.getMember().getId();
        PreferRequest preferRequest = (PreferRequest) session.getAttribute("request");
        ChartResponse chartResponse = recommendService.getChartData(request, preferRequest);
        return ResponseEntity.ok(new Response(chartResponse, "차트 데이터 반환"));
    }
}
