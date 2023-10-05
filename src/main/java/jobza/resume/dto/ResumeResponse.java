package jobza.resume.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResumeResponse {
    private Long resumeId;
    private String originFileName; // 사용자 pdf 명
    private String s3Url; // s3 url 주소
}
