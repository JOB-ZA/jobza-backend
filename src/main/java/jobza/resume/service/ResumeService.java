package jobza.resume.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import jobza.exception.CustomException;
import jobza.exception.ErrorCode;
import jobza.member.entity.Member;
import jobza.member.repository.MemberRepository;
import jobza.member.service.MemberService;
import jobza.resume.entity.Resume;
import jobza.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ResumeService {
    private final AmazonS3Client amazonS3Client;
    private final ResumeRepository resumeRepository;
    private final MemberService memberService;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // https://velog.io/@moon-july5/Spring-Boot-S3-%EC%97%B0%EB%8F%99

    public void uploadResume(MultipartFile resume, Long memberId) {

        String fileExtension = "";
        try {
            String fileName = resume.getOriginalFilename();
            fileExtension = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.FILE_NOT_VALID);
        }

        if (fileExtension.equals("pdf")) {
            String storeFileName = uploadFileToS3(resume);
            log.info("storeImageName: " + storeFileName);
            String s3Url = amazonS3Client.getUrl(bucket, storeFileName).toString();

            Member findMember = memberService.findMemberById(memberId);

            Resume resumeEntity = Resume.builder()
                    .storeFileName(storeFileName)
                    .originFileName(resume.getOriginalFilename())
                    .s3Url(s3Url)
                    .member(findMember)
                    .build();
            resumeRepository.save(resumeEntity);
            log.info("resume DB 저장 완료");
        } else {
            throw new CustomException(ErrorCode.FILE_EXTENSION_NOT_VALID);
        }
    }

    // 이미 등록된 resume 이 있으면 true
    public Optional<Resume> alreadyUploadCheck(Long memberId) {
        return resumeRepository.findByMemberId(memberId);
    }

    private String uploadFileToS3(MultipartFile multipartFile) {
        String storeFileName = createStoreImageName(".pdf");
        InputStream inputStream;
        try {
            inputStream = multipartFile.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            amazonS3Client.putObject(new PutObjectRequest(bucket, storeFileName, inputStream, metadata));
            log.info("s3 업로드 완료");
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
        }
        return storeFileName;
    }
    private String createStoreImageName(String extension) {
        String uuid = UUID.randomUUID().toString();
        String storeFileName = uuid + extension;
        return storeFileName;
    }

    private void deleteFileFromS3(String storeFileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, storeFileName);
        amazonS3Client.deleteObject(deleteObjectRequest);
        log.info("S3 파일 삭제 완료");
    }

    public void deleteResume(Resume resume) {
        deleteFileFromS3(resume.getStoreFileName()); // s3 에서 resume 제거
        resumeRepository.deleteById(resume.getId()); // db 에서 resume 제거
    }
}
