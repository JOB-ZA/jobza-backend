package jobza.resume.dto;

import jobza.resume.entity.Resume;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResumeResponse {
    private Long resumeId;
    private String originFileName; // 사용자 pdf 명
    private String s3Url; // s3 url 주소

    public static ResumeResponse from(Resume resume) {
        return ResumeResponse.builder()
                .resumeId(resume.getId())
                .originFileName(resume.getOriginFileName())
                .s3Url(resume.getS3Url())
                .build();
    }
}
