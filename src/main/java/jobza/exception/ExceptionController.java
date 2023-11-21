package jobza.exception;

import jobza.common.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException e) {
        log.info("핸들링한 에러 발생");
        return ResponseEntity.badRequest().body(new Response(e.getErrorMessage(), "커스텀 예외 반환"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.info("에러: " + e);
        log.info("핸들링하지 않은 에러 발생");
        return ResponseEntity.badRequest().body(new Response(e.getMessage(), "핸들링 하지 않은 에러 반환"));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleCustomException(Exception e, Model model) {
//        log.info("핸들링하지 않은 에러 발생");
//        log.info("exception: " + e);
//        return ResponseEntity.badRequest().body(new Response(e.getMessage()));
//    }
}
