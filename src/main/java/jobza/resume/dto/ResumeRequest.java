package jobza.resume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter @Setter
public class ResumeRequest {
    private MultipartFile resume; // 이력서
}
