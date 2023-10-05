package jobza.resume.entity;

import jakarta.persistence.*;
import jobza.common.BaseEntity;
import jobza.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resume extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "resume_id")
    private Long id;
    private String storeFileName; // 저장할 때 사용한 파일 명
    private String originFileName; // 사용자 pdf 명
    private String s3Url; // s3 url 주소

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
