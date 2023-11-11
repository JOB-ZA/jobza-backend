package jobza.recommend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobza.recommend.dto.JobPostResponse;
import jobza.recommend.dto.PreferRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RecommendService {

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
}