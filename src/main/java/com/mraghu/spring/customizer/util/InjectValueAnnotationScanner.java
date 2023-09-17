package com.mraghu.spring.customizer.util;

import com.mraghu.spring.customizer.annotation.InjectValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;

@Component
@EnableAsync
@Slf4j
public class InjectValueAnnotationScanner {

    @Value(value = "${project.root.package}")
    private String rootPackage;

    /**
     * Scans for classes in a specified package and logs the values of fields annotated with {@link InjectValue}.
     *
     * @throws ClassNotFoundException if a class could not be found
     */
    @Async
    public void scanAndLogValueAnnotations() {
        try {
            ClassPathScanningCandidateComponentProvider scanner =
                    new ClassPathScanningCandidateComponentProvider(true);
            scanner.addIncludeFilter(new AnnotationTypeFilter(InjectValue.class));

            Set<BeanDefinition> components = scanner.findCandidateComponents(rootPackage);
            for (BeanDefinition component : components) {

                Class<?> clazz = Class.forName(component.getBeanClassName());
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    InjectValue valueAnnotation = AnnotationUtils.getAnnotation(field, InjectValue.class);
                    if (valueAnnotation != null) {

                        log.info("Value is {}", valueAnnotation.value());
                        log.info("Name is {}", valueAnnotation.name());
                        log.info("Expire date is {}", valueAnnotation.expireDate());
                        log.info("Description is {}", valueAnnotation.description());

                        log.info("Used by is {}", Arrays.stream(valueAnnotation.usedBy())
                                .toList());

                    }
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while scanning and logging value annotations", e);
        }

    }
}
