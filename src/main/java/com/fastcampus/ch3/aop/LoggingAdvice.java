package com.fastcampus.ch3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Aspect
//부가기능이 담긴 메서드
public class LoggingAdvice {
    //(반환타입(접근제어자 가능,생략가능) 패키지명.클래스명.메서드명(매개변수 목록(갯수상관x모든타입ok))

    @Around("execution(* com.fastcampus.ch3.aop.MyMath.add*(..))")//before, after 다추가 //pointcut -부가기능이 적용될 메서드의 패턴
    public Object methodCalllog(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();//메서드 수행 시간을 알기위해 시작시간 저장
        System.out.println("<<[start]"+pjp.getSignature().getName()+ Arrays.toString(pjp.getArgs()));  //before advice
        //getSignature 메소드의(리턴타입, 이름, 매개변수)정보가 저장된 객체를리턴

        Object result = pjp.proceed();//target의 메서드를 호출

        System.out.println("result="+result);
        System.out.println("[end]>>"+(System.currentTimeMillis()-start)+"ms");
        return result;




    }
}
