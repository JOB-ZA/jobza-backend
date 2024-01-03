package jobza.resume.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    @Async
    public void testtest(int i) {
        try {
            Thread.sleep(1000);
            log.info("async i = " + i);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
