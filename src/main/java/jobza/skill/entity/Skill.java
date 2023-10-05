package jobza.skill.entity;

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
public class Skill extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "skill_id")
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
