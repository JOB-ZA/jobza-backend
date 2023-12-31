package jobza.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import jobza.common.response.Response;
import jobza.member.dto.MyPageResponse;
import jobza.member.dto.TokenRequest;
import jobza.member.service.MemberService;
import jobza.member.service.OauthService;
import jobza.security.principal.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final OauthService oauthService;
    private final MemberService memberService;

    @Operation(summary = "카카오 로그인", description = "카카오 로그인이라 아래 링크로 이동해서 로그인 테스트 진행 하면 됩니다<br>https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=de47672601f1318713d6ebbfca037682&redirect_uri=http://localhost:3000/auth")
    @GetMapping("/kakaoLogin")
    public void login() {
    }

    @GetMapping("/login/oauth2/code")
    public ResponseEntity<Response> redirect(
            @RequestParam("code") String code
            , @RequestParam(required = false ,value = "state") String state) {
        log.info("auth code 응답 완료: " + code);
        return
                oauthService.redirect(
                        TokenRequest.builder()
                                .registrationId("kakao")
                                .code(code)
                                .state(state)
                                .build());
    }

    @GetMapping("/myPage")
    public ResponseEntity<Response> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        MyPageResponse myPageResponse = memberService.getMyInfo(principalDetails.getMember().getId());

        // todo: 마이페이지 반환 정보 추후에 더 추가
        return ResponseEntity.ok(new Response(myPageResponse, "마이페이지 일부 정보 반환"));
    }
}
