package jobza.member.service;

import jobza.exception.CustomException;
import jobza.exception.ErrorCode;
import jobza.member.dto.MyPageResponse;
import jobza.member.entity.Member;
import jobza.member.repository.MemberRepository;
import jobza.resume.dto.ResumeResponse;
import jobza.resume.entity.Resume;
import jobza.resume.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {
    private final ResumeService resumeService;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MyPageResponse getMyInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Optional<Resume> findResume = resumeService.getResumeInfo(member.getId());


        return MyPageResponse.from(member, findResume);
    }
}
