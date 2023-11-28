package jobza.recommend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jobza.exception.CustomException;
import jobza.exception.ErrorCode;
import jobza.recommend.dto.ChartRequest;
import jobza.recommend.dto.ChartResponse;
import jobza.recommend.dto.JobPostResponse;
import jobza.recommend.dto.PreferRequest;
import jobza.recommend.entity.Employment;
import jobza.recommend.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                "python", path, request.getJob(),
                request.getPark(), request.getCafe(), request.getFastFood(),
                request.getMart(), request.getHospital(), request.getHealth());
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

    public JobPostResponse findByWantedAuthNoWithPython(String wantedAuthNo) throws IOException {
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

    public Employment findByWantedAuthNo(String wantedAuthNo) {
        return jobRepository.findByWantedAuthNo(wantedAuthNo)
                .orElseThrow(() -> new CustomException(ErrorCode.JOB_NOT_FOUND));
    }


    public ChartResponse getChartData(ChartRequest request) throws IOException {
        String path = "src/main/java/jobza/pythonApi/datas/charts.py";
        String type = "";
        if (request.getIsPie() && !request.getIsRadar()) {
            type = "pie";
        } else if (!request.getIsPie() && request.getIsRadar()){
            type = "radar";
        } else {
            throw new CustomException(ErrorCode.CHART_TYPE_NOT_VALID);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "python", path, request.getWantedAuthNo(),
                request.getPark(), request.getCafe(), request.getFastFood(),
                request.getMart(), request.getHospital(), request.getHealth(),
                type);
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
        ChartResponse chartResponse = objectMapper.readValue(jsonDataFromPython, ChartResponse.class);

        return chartResponse;
    }


}

//{
//    "names": ['공원','스타벅스','맥도날드','대형마트','병원','헬스장'],
//    "pie": [0.0, 0.11863219506239386, 0.0, 0.003182329878216937, 0.0, 0.0015782534311454114]
//}
//
//{
//    "names": ['공원','스타벅스','맥도날드','대형마트','병원','헬스장'],
//    "min": [5, 3, 13, 9, 5, 3],
//    "max": [29, 29, 16, 18, 26, 28]
//}