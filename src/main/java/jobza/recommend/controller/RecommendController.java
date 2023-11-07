package jobza.recommend.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.recommend.dto.JobPostResponse;
import jobza.recommend.dto.PreferRequest;
import jobza.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendController {
    private final RecommendService recommendService;

    @Operation(summary = "맞춤 공고 요청", description = "직업 선택 및 편의시설 선호도에 따라 맞춤 공고 데이터 반환")
    @PostMapping("/job-post/prefer")
    public ResponseEntity<Response> jobPostByMemberPrefer(@RequestBody PreferRequest request) throws IOException {
        List<JobPostResponse> responseList = recommendService.jobPostByMemberPrefer(request);
        return ResponseEntity.ok(new Response(responseList, "맞춤 공고 데이터 반환"));
    }
}
