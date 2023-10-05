package jobza.resume.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Resume {
    @Id @GeneratedValue
    @Column(name = "resume_id")
    private Long id;

    private String originFileName; // 사용자 pdf 명
    private String s3Url; // s3 url 주소
}
