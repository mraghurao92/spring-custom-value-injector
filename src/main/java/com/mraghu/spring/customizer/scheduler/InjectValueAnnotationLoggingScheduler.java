package com.mraghu.spring.customizer.scheduler;

import com.mraghu.spring.customizer.util.InjectValueAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@ConditionalOnProperty(name = "inject-value.log.scheduler", havingValue = "true", matchIfMissing = true)
public class InjectValueAnnotationLoggingScheduler {
    private static final String LOGGING_CRON_EXPRESSION = "${inject-value-annotation.logging.cron:0 0/60 * * * ?}";
    @Autowired
    private InjectValueAnnotationScanner injectValueAnnotationScanner;


    /**
     * Logs value annotations periodically.
     * This method uses the {@code @Scheduled} annotation to specify the cron expression for when the method should be executed.
     * The cron expression is set to run every 60 minutes.
     */
    @Scheduled(cron = LOGGING_CRON_EXPRESSION)
    public void logValueAnnotationsPeriodically() {

        injectValueAnnotationScanner.scanAndLogValueAnnotations();


    }
}
