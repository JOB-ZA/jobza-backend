package jobza.resume.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jobza.exception.CustomException;
import jobza.exception.ErrorCode;
import jobza.resume.dto.JobRecommendResponse;
import jobza.skill.dto.SkillResponse;
import jobza.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ResumeAnalyzer {
    private final ResourcePatternResolver resourcePatternResolver;
    private final SkillService skillService;
    public String pdfToText(InputStream inputStream) throws IOException {
        PDDocument document = PDDocument.load(inputStream);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }
    public List<JobRecommendResponse> recommendJobWithResume(InputStream pdf) throws IOException, CsvException {
        // pdf -> text 추출
        String text = pdfToText(pdf);
        text = text.toUpperCase();
        final String finalText = text;
        Resource[] resources = resourcePatternResolver.getResources("classpath:_skillspr/*.csv");

        List<JobRecommendResponse> results = new ArrayList<>();
        Set<String> userSkills = new HashSet<>(); // 추가

        ExecutorService executorService = Executors.newFixedThreadPool(resources.length);
        List<Future<JobRecommendResponse>> futures = new ArrayList<>();

        // csv 파일 순회
        for (Resource resource : resources) {

            Future<JobRecommendResponse> future = executorService.submit(() -> {
                List<String[]> records;
                try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                    records = csvReader.readAll();
                } catch (Exception e) {
                    log.info("멀티 스레딩 작업 중 에러");
                    throw new CustomException(ErrorCode.MULTI_THREADING_ERROR);
                }
                double pr = 0;
                boolean skip = true;
                // 개별 csv 데이터 순회
                for (String[] record : records) {
                    // 0번째 행 skip
                    if (skip) {
                        skip = false;
                        continue;
                    }

                    // skill 추출
                    String skill = record[1];
                    double probability = Double.parseDouble(record[5]);

                    // skill 이 pdf 내용에 있는 단어일 때
                    if (finalText.contains(skill) && (skill.length() > 1 || "C".equals(skill))) {
                        pr += Math.log(probability);
                        userSkills.add(skill); // 추가
                    }
                }
                if (pr == 1) pr = 0;
                return new JobRecommendResponse(resource.getFilename().substring(0, resource.getFilename().length() - 4), pr);
            });
            futures.add(future);
        }

        // 모든 작업이 완료될 때까지 대기하고 결과를 수집합니다.
        for (Future<JobRecommendResponse> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                log.info("멀티 스레딩 작업 중 에러");
                throw new CustomException(ErrorCode.MULTI_THREADING_ERROR);
            }
        }
        // ExecutorService 종료
        executorService.shutdown();

        results.sort(Comparator.comparing(JobRecommendResponse::getProbability).reversed());
        return results;
    }

    public List<JobRecommendResponse> recommendJobWithSkill(Long memberId) throws IOException, CsvException {
        List<String> skillList = skillService.findAllSkill(memberId).stream()
                .map(SkillResponse::getSkillName)
                .collect(Collectors.toList());
        String text = String.join(",", skillList);

        Resource[] resources = resourcePatternResolver.getResources("classpath:_skillspr/*.csv");

        List<JobRecommendResponse> results = new ArrayList<>();
        Set<String> userSkills = new HashSet<>(); // 추가

        ExecutorService executorService = Executors.newFixedThreadPool(resources.length);
        List<Future<JobRecommendResponse>> futures = new ArrayList<>();

        // csv 파일 순회
        for (Resource resource : resources) {
            Future<JobRecommendResponse> future = executorService.submit(() -> {
                List<String[]> records;
                try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                    records = csvReader.readAll();
                } catch (Exception e) {
                    log.info("멀티 스레딩 작업 중 에러");
                    throw new CustomException(ErrorCode.MULTI_THREADING_ERROR);
                }
                double pr = 0;
                boolean skip = true;
                // 개별 csv 데이터 순회
                for (String[] record : records) {
                    // 0번째 행 skip
                    if (skip) {
                        skip = false;
                        continue;
                    }

                    // skill 추출
                    String skill = record[1];
                    double probability = Double.parseDouble(record[5]);

                    // skill 이 pdf 내용에 있는 단어일 때
                    if (text.contains(skill) && (skill.length() > 1 || "C".equals(skill))) {
                        pr += Math.log(probability);
                        userSkills.add(skill); // 추가
                    }
                }
                if (pr == 1) pr = 0;
                return new JobRecommendResponse(resource.getFilename().substring(0, resource.getFilename().length() - 4), pr);
            });
            futures.add(future);
        }

        // 모든 작업이 완료될 때까지 대기하고 결과를 수집합니다.
        for (Future<JobRecommendResponse> future : futures) {
            try {
                results.add(future.get());
            } catch (Exception e) {
                log.info("멀티 스레딩 작업 중 에러");
                throw new CustomException(ErrorCode.MULTI_THREADING_ERROR);
            }
        }
        // ExecutorService 종료
        executorService.shutdown();

        results.sort(Comparator.comparing(JobRecommendResponse::getProbability).reversed());
        return results;
    }

}
