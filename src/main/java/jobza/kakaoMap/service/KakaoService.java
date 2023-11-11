package jobza.kakaoMap.service;

import io.netty.util.internal.ObjectUtil;
import jakarta.transaction.Transactional;
import jobza.exception.CustomException;
import jobza.exception.ErrorCode;
import jobza.kakaoMap.dto.KakaoApiAddressRequest;
import jobza.kakaoMap.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class KakaoService {
    private final RestTemplate restTemplate;
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String CLIENT_ID;

    public String callKakaoApi(KakaoApiAddressRequest request) {
        if (ObjectUtils.isEmpty(request.getAddress())) {
            throw new CustomException(ErrorCode.KAKAO_API_NEED_ADDRESS);
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", request.getAddress());
        URI uri = uriBuilder.build().encode().toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + CLIENT_ID);
        HttpEntity http = new HttpEntity(headers);
        KakaoApiResponseDto kakaoApiResponseDto = restTemplate.exchange(uri, HttpMethod.GET, http, KakaoApiResponseDto.class).getBody();

        KakaoApiResponseDto.DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);
        double latitude = documentDto.getLatitude();
        double longitude = documentDto.getLongitude();
        log.info("위도 경도로 변환 완료. 위도: " + latitude + ", 경도: " + longitude);

        return ROAD_VIEW_BASE_URL + latitude + "," + longitude;
    }
}
