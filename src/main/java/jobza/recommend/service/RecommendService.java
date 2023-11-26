package jobza.recommend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobza.exception.CustomException;
import jobza.exception.ErrorCode;
import jobza.recommend.dto.JobPostResponse;
import jobza.recommend.dto.PreferRequest;
import jobza.recommend.entity.Employment;
import jobza.recommend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecommendService {
    private final JobRepository jobRepository;

    public List<JobPostResponse> jobPostByMemberPrefer(PreferRequest request) throws IOException {
        String path = "src/main/java/jobza/pythonApi/datas/select_job.py";
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python", path, request.getJob(), request.getCafe(),
                request.getHospital(), request.getHealth(), request.getPark(),
                request.getMart(), request.getFastFood());
        processBuilder.redirectErrorStream(true); // 표준 오류 스트림을 표준 출력 스트림으로 병합
        Process result = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result.getInputStream(), "euc-kr"));

        // JSON 데이터를 저장하기 위한 StringBuilder
        StringBuilder jsonDataBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            jsonDataBuilder.append(line);
        }

        // JSON 데이터 문자열
        String jsonDataFromPython = jsonDataBuilder.toString();
        System.out.println(jsonDataFromPython);
        // JSON 데이터를 JobPostResponse 배열로 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JobPostResponse[] myDTOs = objectMapper.readValue(jsonDataFromPython, JobPostResponse[].class);

        // List 로 변환
        List<JobPostResponse> responseList = new ArrayList<>();
        for (JobPostResponse j : myDTOs) {
            responseList.add(j);
        }
        return responseList;
    }

    public Employment findByWantedAuthNo(@RequestParam("wantedAuthNo") String wantedAuthNo) {
        return jobRepository.findByWantedAuthNo(wantedAuthNo)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_NOT_FOUND));
    }

    public JobPostResponse findByWantedAuthNoWithPython(@RequestParam("wantedAuthNo") String wantedAuthNo) throws IOException {
        String path = "src/main/java/jobza/pythonApi/datas/getData.py";
        ProcessBuilder processBuilder = new ProcessBuilder(
                "python", path, wantedAuthNo);
        processBuilder.redirectErrorStream(true); // 표준 오류 스트림을 표준 출력 스트림으로 병합
        Process result = processBuilder.start();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(result.getInputStream(), "euc-kr"));

        // JSON 데이터를 저장하기 위한 StringBuilder
        StringBuilder jsonDataBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            jsonDataBuilder.append(line);
        }

        // JSON 데이터 문자열
        String jsonDataFromPython = jsonDataBuilder.toString();
        System.out.println(jsonDataFromPython);
        // JSON 데이터를 JobPostResponse 배열로 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JobPostResponse jobPostResponse = objectMapper.readValue(jsonDataFromPython, JobPostResponse.class);

        return jobPostResponse;
    }
}
