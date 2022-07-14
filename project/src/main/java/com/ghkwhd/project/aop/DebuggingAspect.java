package com.ghkwhd.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // AOP 클래스 선언, 부가 기능을 주입하는 클래스
@Component  // IoC Container가 해당 객체를 생성 및 관리
@Slf4j
public class DebuggingAspect {

    // 대상 메소드 선택
    @Pointcut("execution(* com.ghkwhd.project.api.*.*(..))")   // 어느 지점을 타켓으로 하여 부가기능을 삽입할 것인지
    private void cut() {

    }

    // 실행 시점 설정 : cut()의 대상이 수행되기 이전
    @Before("cut()")    // 부가 기능이 cut()에 지정된 Pointcut을 대상으로 아래 메소드가 실행된다
    // 입력되는 전달값이 무엇인지 logging을 위해 작성
    public void loggingArgs(JoinPoint joinPoint) {
        // joingPoint : cut()의 대상 메소드 (메소드를 둘러싼 지점?)

        // 입력값 가져오기
        Object[] args = joinPoint.getArgs();

        // 클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature().getName();

        // 입력값 로깅하기
        for(Object obj : args) {
           log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }

    }

    // 실행 시점 설정 : cut()에 지정된 대상 호출 성공 후 수행
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void loggingReturnValue(JoinPoint joinPoint,  // cut의 대상 메소드
                                   Object returnObj) {   // return 값

        // 클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature().getName();

        // 반환값 로깅
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);

    }

}
