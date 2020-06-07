package com.hai.example.demo.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect
{
    //NOTE: point cut expression
    //https://howtodoinjava.com/spring-aop/aspectj-pointcut-expressions/

    private static final Logger LOGGER = LogManager.getLogger(LoggingAspect.class);

    @Around("execution(* com.hai.example.demo.controllers..*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        LOGGER.info("...Hai Execution time of " + className + "." + methodName + " "
                + ":: " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }

    //https://howtodoinjava.com/spring-aop/aspectj-before-annotation-example/
    @Before("execution(* com.hai.example.demo.controllers..*(..))")
    public void logBeforeAllMethods(JoinPoint joinPoint)
    {
        System.out.println("****HAI LOG BEFORE LoggingAspect.logBeforeAllMethods() : " + joinPoint.getSignature().getName());
    }

    @Before("execution(* com.hai.example.demo.controllers.HomeController.*(..))")
    public void logBeforeHome(JoinPoint joinPoint)
    {
        System.out.println("****HAI LOG BEFORE LoggingAspect.HomeController() : " + joinPoint.getSignature().getName());
    }



    //https://howtodoinjava.com/spring-aop/aspectj-after-annotation-example/
    @After("execution(* com.hai.example.demo.controllers..*(..))")
    public void logAfterAllMethods(JoinPoint joinPoint)
    {
        System.out.println("****Hai LOG AFTER LoggingAspect.logAfterAllMethods() : " + joinPoint.getSignature().getName());
    }
    @After("execution(* com.hai.example.demo.controllers.HomeController.*(..))")
    public void logAfterHome(JoinPoint joinPoint)
    {
        System.out.println("****Hai LOG AFTER LoggingAspect.HomeController() : " + joinPoint.getSignature().getName());
    }


    //https://howtodoinjava.com/spring-aop/aspectj-after-returning-annotation-example/
    @AfterReturning("execution(* com.hai.example.demo.controllers..*(..))")
    public void logAfterReturningAllMethods() throws Throwable
    {
        System.out.println("****HAI AFTER-RETURNING-ALL LoggingAspect.logAfterReturningAllMethods() ");
    }
    @AfterReturning(pointcut="execution(* com.hai.example.demo.controllers.HomeController.getStatus(..))", returning="retVal")
    public void logAfterReturningHomeStatus(Object retVal) throws Throwable
    {
        System.out.println("****AFTER-RETURNING:HomeController.getStatus LoggingAspect.logAfterReturningGetEmployee() ");
        System.out.println(retVal);
    }

    @AfterThrowing (pointcut = "execution(* com.hai.example.demo.controllers..*(..))", throwing = "ex")
    public void logAfterThrowingAllMethods(Exception ex) throws Throwable
    {
        System.out.println("****Hai LoggingAspect.logAfterThrowingAllMethods() " + ex);
    }
}