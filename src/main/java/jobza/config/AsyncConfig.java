package jobza.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    private static int CORE_POOL_SIZE = 500; // 동시에 실행할 쓰레드의 갯수를 의미, default 값은 1이다.
    private static int MAX_POOL_SIZE = 3000; // 쓰레드 풀의 최대 크기를 지정, default 값은 Integer.MAX_VALUE
    private static int QUEUE_CAPACITY = 5000; // 큐의 크기를 지정, default 값은 Integer.MAX_VALUE 이다.
    private static String THREAD_NAME_PREFIX = "async-task";

    @Bean
    public Executor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }
}
