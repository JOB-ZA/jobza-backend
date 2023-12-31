package jobza.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    MEMBER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다."),

    ACCESS_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 access token 입니다."),

    FILE_NOT_VALID(HttpStatus.BAD_REQUEST, "파일이 없거나 잘못된 접근입니다."),
    FILE_EXTENSION_NOT_VALID(HttpStatus.BAD_REQUEST, "pdf 파일 형식이 아닙니다."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "pdf 파일 업로드 중 에러가 발생했습니다."),
    RESUME_NOT_FOUND(HttpStatus.NOT_FOUND, "resume 데이터를 찾을 수 없습니다."),
    MULTI_THREADING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "병렬 처리 과정 중 예외가 발생했습니다."),

    KAKAO_API_NEED_ADDRESS(HttpStatus.BAD_REQUEST, "로드뷰로 띄우기 위해 요청된 주소가 없습니다."),
    JOB_NOT_FOUND(HttpStatus.BAD_REQUEST, "직업 공고 MongoDB에 데이터가 존재하지 않습니다."),

    CHART_TYPE_NOT_VALID(HttpStatus.BAD_REQUEST, "차트 타입은 radar pie 중 하나만 true, 나머진 false여야 합니다.");


    private final HttpStatus status;
    private final String errorMessage;

}
