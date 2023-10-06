package jobza.resume.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter @Setter
public class ResumeRequest {
    @Schema(description = "이력서 업로드")
    private MultipartFile resume; // 이력서
}
