package jobza.skill.repository;

import jobza.skill.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    @Query("select s from Skill s where s.member.id = :memberId")
    List<Skill> findByMemberId(@Param("memberId") Long memberId);

    @Query("select s from Skill s where s.member.id = :memberId")
    Optional<Skill> existsSkillByMember(@Param("memberId") Long memberId);

    @Modifying
    @Query("delete from Skill s where s.member.id = :memberId")
    void deleteByMemberId(@Param("memberId") Long memberId);
}
