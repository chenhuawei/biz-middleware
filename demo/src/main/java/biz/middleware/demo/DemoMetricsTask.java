package biz.middleware.demo;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DemoMetricsTask {

    private volatile int counter;

    @Scheduled(fixedRate = 5000)
    @Timed(value = "http.DemoMetricsTask", description = "Time spent task")
    public void testMetrics() throws InterruptedException {
        counter ++;
        log.debug("task start {}", counter);
        Thread.sleep(counter % 2 == 0 ? 1000L : 5000L);

    }
}
