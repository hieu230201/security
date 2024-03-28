package com.example.securitybase.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StopWatch;
import com.example.securitybase.logging.LogManage;
import com.example.securitybase.logging.bases.ILogManage;

/*@Aspect
@Component*/
public class LoggingAspect {
    private static final ILogManage logger = LogManage.getLogManage(LoggingAspect.class);

    // AOP expression for which methods shall be intercepted
    @Around("execution(public * com.mbbank.cmv.service..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

// Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

// Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

// Log method execution time
        if (stopWatch.getTotalTimeMillis() > 10000)
            logger.info("Method Execution long time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis()
                    + " ms");

        return result;
    }
}
