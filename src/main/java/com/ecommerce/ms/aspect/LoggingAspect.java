package com.ecommerce.ms.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.argument.StructuredArguments;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("execution(* com.ecommerce.ms.user..*(..))")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Serialize method arguments to JSON
        String argsJson = objectMapper.writeValueAsString(joinPoint.getArgs());

        logger.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                StructuredArguments.keyValue("args", argsJson)
        );

        Object result = joinPoint.proceed();

        // Serialize result to JSON
        String resultJson = objectMapper.writeValueAsString(result);
        long duration = System.currentTimeMillis() - startTime;

        logger.info("Exiting method: {} with result: {} in {} ms",
                joinPoint.getSignature().toShortString(),
                StructuredArguments.keyValue("result", resultJson),
                StructuredArguments.keyValue("duration", duration)
        );

        return result;
    }
}
