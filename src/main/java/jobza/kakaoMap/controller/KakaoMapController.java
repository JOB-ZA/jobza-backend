package jobza.kakaoMap.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.kakaoMap.dto.KakaoApiAddressRequest;
import jobza.kakaoMap.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class KakaoMapController {
    private final KakaoService kakaoService;

    @Operation(summary = "로드뷰 url 반환", description = "회사 주소를 기반으로 로드뷰 url 반환")
    @PostMapping("/kakao-api/roadview")
    public ResponseEntity<Response> kakaoApiRoadView(@RequestBody KakaoApiAddressRequest request) {
        String roadViewUrl = kakaoService.callKakaoApi(request);
        return ResponseEntity.ok(new Response(roadViewUrl, "카카오 로드뷰 주소 반환 완료"));
    }
}
