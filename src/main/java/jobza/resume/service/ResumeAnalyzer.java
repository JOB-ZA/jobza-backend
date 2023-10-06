package jobza.resume.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jobza.resume.dto.JobRecommendResponse;
import jobza.skill.dto.SkillResponse;
import jobza.skill.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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

        Resource[] resources = resourcePatternResolver.getResources("classpath:_skillspr/*.csv");

        List<JobRecommendResponse> results = new ArrayList<>();
        Set<String> userSkills = new HashSet<>(); // 추가

        // csv 파일 순회
        for (Resource resource : resources) {
            List<String[]> records;
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                records = csvReader.readAll();
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
            results.add(new JobRecommendResponse(resource.getFilename().substring(0, resource.getFilename().length() - 4), pr));
        }
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

        // csv 파일 순회
        for (Resource resource : resources) {
            List<String[]> records;
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                records = csvReader.readAll();
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
            results.add(new JobRecommendResponse(resource.getFilename().substring(0, resource.getFilename().length() - 4), pr));
        }
        results.sort(Comparator.comparing(JobRecommendResponse::getProbability).reversed());
        return results;
    }

    static class Result {
        private String job;
        private double probability;

        public Result(String job, double probability) {
            this.job = job;
            this.probability = probability;
        }

        public double getProbability() {
            return probability;
        }

        @Override
        public String toString() {
            return job + ": " + probability;
        }
    }

}
