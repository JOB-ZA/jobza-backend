package jobza.recommend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobPostListResponse {
    private String wantedAuthNo;
    private CorpInfo corpInfo;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Getter
    @Setter
    static class CorpInfo {
        private String corpNm;
    }
}
