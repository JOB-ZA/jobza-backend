package jobza.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.member.dto.TokenRequest;
import jobza.member.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final OauthService oauthService;

    @Operation(summary = "카카오 로그인", description = "카카오 로그인이라 아래 링크로 이동해서 로그인 테스트 진행 하면 됩니다<br>https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=de47672601f1318713d6ebbfca037682&redirect_uri=http://localhost:8080/login/oauth2/code/kakao")
    @GetMapping("/kakaoLogin")
    public void login() {
    }


    @GetMapping("/login/oauth2/code/{registrationId}")
    public ResponseEntity<Response> redirect(
            @PathVariable("registrationId") String registrationId
            , @RequestParam("code") String code
            , @RequestParam(required = false ,value = "state") String state) {
        log.info("auth code 응답 완료: " + code);
        log.info("state 응답 완료: " + state);
        return
                oauthService.redirect(
                        TokenRequest.builder()
                                .registrationId(registrationId)
                                .code(code)
                                .state(state)
                                .build());
    }
}
