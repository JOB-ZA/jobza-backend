package jobza.member.dto;

import jobza.member.entity.Member;
import jobza.resume.dto.ResumeResponse;
import jobza.resume.entity.Resume;
import lombok.*;

import java.util.Optional;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MyPageResponse {
    private String name; // 사용자 실명
    private String profileImage; // 카카오 프로필 사진
    private String role; // 일반 회원 or 기업 회원
    private ResumeResponse resumeResponse; // 이력서 정보

    public static MyPageResponse from(Member member, Optional<Resume> findResume) {
        ResumeResponse resumeResponse = findResume.isPresent() ? ResumeResponse.from(findResume.get()) : null;
        return MyPageResponse.builder()
                .name(member.getName())
                .profileImage(member.getProfileImage())
                .role(member.getRole().getDescription())
                .resumeResponse(resumeResponse)
                .build();
    }
}
