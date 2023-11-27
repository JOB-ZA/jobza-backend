package jobza.recommend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChartRequest {
    @Schema(description = "wantedAuthNo 값", example = "KJBO002307200001")
    private String wantedAuthNo;
    @Schema(description = "레이더 차트 인지 여부", example = "true")
    private Boolean isRadar;
    @Schema(description = "파이 차트 인지 여부", example = "false")
    private Boolean isPie;
//    @Schema(description = "선택 직업", example = "웹 개발자")
//    private String job; // 직업
//    @Schema(description = "카페에 대한 가중치", example = "0.5")
//    private String cafe;
//    @Schema(description = "병원에 대한 가중치", example = "0.5")
//    private String hospital;
//    @Schema(description = "헬스장에 대한 가중치", example = "0.5")
//    private String health;
//    @Schema(description = "공원에 대한 가중치", example = "0.5")
//    private String park;
//    @Schema(description = "대형마트 대한 가중치", example = "0.5")
//    private String mart;
//    @Schema(description = "페스트푸드점에 대한 가중치", example = "0.5")
//    private String fastFood;
}
