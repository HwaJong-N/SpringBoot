package com.ghkwhd.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    // 특정 어노테이션을 대상 지정
    @Pointcut("@annotation(com.ghkwhd.project.annotation.RunningTime)")
    private void enableRunningTIme() {}

    // 기본 패키지의 모든 메소드
    @Pointcut("execution(* com.ghkwhd.project..*.*(..))")
    private void cut() {}

    // 실행 시점 설정 : 두 조건을 모두 만족하는 대상을 전후로 부가 기능을 삽입
    @Around("cut() && enableRunningTIme()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        // ProceddingJoinPoing : 대상을 실행까지 할 수 있는 joingPoint

        // 메소드 수행 전, 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 메소드 수행
        Object returningObj = joinPoint.proceed();    // 타켓팅이 된 대상을 수행
        String methodName = joinPoint.getSignature().getName();

        // 메소드 수행 후, 측정 종료 및 로깅
        stopWatch.stop();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }

}
