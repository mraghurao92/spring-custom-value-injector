package com.mraghu.spring.customizer.util;

import com.mraghu.spring.customizer.annotation.InjectValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@EnableAsync
public class InjectValueAnnotationScanner {

    //Add Logger
    private static final Logger log = LoggerFactory.getLogger(InjectValueAnnotationScanner.class);
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
            Map<String, Map<String, Object>> valueAnnotationMap = new HashMap<>();
            Map<String, Object> injectValueLogMap = new HashMap<>();
            for (BeanDefinition component : components) {

                Class<?> clazz = Class.forName(component.getBeanClassName());
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    InjectValue valueAnnotation = AnnotationUtils.getAnnotation(field, InjectValue.class);
                    if (valueAnnotation != null) {
                        injectValueLogMap = populateMapForLoggingPropertyUsage(valueAnnotation);
                    }
                }
                if (!injectValueLogMap.isEmpty())
                    valueAnnotationMap.put(clazz.getName(), injectValueLogMap);
            }
            if (!valueAnnotationMap.isEmpty())
                log.info("@InjectValue contents: {}", valueAnnotationMap);
        } catch (Exception e) {
            log.error("Error occurred while scanning and logging value annotations", e);
        }

    }

    /**
     * Populates the valueAnnotationMap with logging information for a property usage.
     *
     * @param valueAnnotation the InjectValue annotation containing the property information
     */

    private Map<String, Object> populateMapForLoggingPropertyUsage(InjectValue valueAnnotation) {
        Map<String, Object> injectValueLogMap = new HashMap<>();
        injectValueLogMap.put("property", valueAnnotation.value());
        injectValueLogMap.put("name", valueAnnotation.name());
        injectValueLogMap.put("expirationDate", valueAnnotation.expireDate());
        injectValueLogMap.put("propertyDescription", valueAnnotation.description());
        injectValueLogMap.put("propertyBeingUsedBy", Arrays.stream(valueAnnotation.referencedBy())
                .toList());
        injectValueLogMap.put("propertyBeingReferredFrom", Arrays.stream(valueAnnotation.referencedFrom())
                .toList());

        return injectValueLogMap;
    }
}