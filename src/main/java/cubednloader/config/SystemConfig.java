package cubednloader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class SystemConfig {
    // 스레드풀 설정
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(64);
        executor.setThreadNamePrefix("CubeDownloaderExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true); // 종료 시 남은 작업 처리 후 종료
        executor.setAwaitTerminationSeconds(30); // 최대 30초 동안 종료 대기
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 거부 정책 설정
        executor.initialize(); // 스레드풀 초기화

        return executor;
    }
}
