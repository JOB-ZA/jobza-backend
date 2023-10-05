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
    REFRESH_TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "만료된 refresh token 입니다."),
    REFRESH_TOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "잘못된 refresh token 요청 입니다."),
    REFRESH_TOKEN_UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "refresh token 재발급 중 알 수 없는 에러 발생."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입니다."),

    FILE_NOT_VALID(HttpStatus.BAD_REQUEST, "파일이 없거나 잘못된 접근입니다."),
    FILE_EXTENSION_NOT_VALID(HttpStatus.BAD_REQUEST, "pdf 파일 형식이 아닙니다."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "pdf 파일 업로드 중 에러가 발생했습니다.");


    private final HttpStatus status;
    private final String errorMessage;

}
