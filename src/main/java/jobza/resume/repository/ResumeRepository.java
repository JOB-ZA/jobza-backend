package jobza.resume.repository;

import jobza.resume.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    @Query("select r from Resume r where r.member.id = :memberId")
    Optional<Resume> findByMemberId(@Param("memberId") Long memberId);
}
