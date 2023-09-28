package jobza.member.entity;

import jakarta.persistence.*;
import jobza.common.BaseEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username; // 카카오 아이디
    private String name; // 카카오 이름
    private String profileImage; // 프로필 이미지
    @Enumerated(EnumType.STRING)
    private Role role; // 권한
    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider; // 인가서버

}
