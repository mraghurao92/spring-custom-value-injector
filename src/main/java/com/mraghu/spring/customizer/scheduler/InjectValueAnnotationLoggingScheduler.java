package com.mraghu.spring.customizer.scheduler;

import com.mraghu.spring.customizer.util.InjectValueAnnotationScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j

public class InjectValueAnnotationLoggingScheduler {
    @Autowired
    private InjectValueAnnotationScanner injectValueAnnotationScanner;


    /**
     * Logs value annotations periodically.
     * This method uses the {@code @Scheduled} annotation to specify the cron expression for when the method should be executed.
     * The cron expression is set to run every 60 minutes.
     */
    @Scheduled(cron = "${inject-value-annotation.logging.cron:0 0/60 * * * ?}")
    public void logValueAnnotationsPeriodically() {

        injectValueAnnotationScanner.scanAndLogValueAnnotations();


    }
}
