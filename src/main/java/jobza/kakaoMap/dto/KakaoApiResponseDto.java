package jobza.kakaoMap.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoApiResponseDto {
    @JsonProperty("meta")
    private MetaDto metaDto;
    @JsonProperty("documents")
    private List<DocumentDto> documentDtoList;

    @Getter @Setter
    public static class MetaDto {
        @JsonProperty("total_count")
        private Integer totalCount;
    }
    @Getter @Setter
    public static class DocumentDto {
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("y")
        private double latitude; // 위도
        @JsonProperty("x")
        private double longitude; // 경도
        @JsonProperty("place_name")
        private String placeName;
        @JsonProperty("distance")
        private double distance;
    }
}