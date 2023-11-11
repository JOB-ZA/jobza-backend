package jobza.kakaoMap.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KakaoApiAddressRequest {
    @Schema(description = "회사 주소", example = "부산광역시 해운대구 수영강변대로 140, 부산문화콘텐츠컴플렉스 612호")
    private String address;
}
