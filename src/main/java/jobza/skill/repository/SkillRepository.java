package jobza.skill.repository;

import jobza.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("select s from Skill s where s.member.id = :memberId")
    List<Skill> findByMemberId(Long memberId);
}
