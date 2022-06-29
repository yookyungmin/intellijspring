package com.fastcampus.ch3.aop;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AopMain2 {
    public static void main(String[] args) {
        ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context_aop.xml");
        
        MyMath mm = (MyMath)ac.getBean("myMath");
        mm.add(3,5);
        mm.add(1,2,3);
        System.out.println("mm.multiply(3,5) = " + mm.multiply(3, 5)); //add만 적용되게 해서 호출은 됐는데 출력이안댐
//        System.out.println("mm.add(3,5) = " + mm.add(3, 5));  //.soutv 하면 자동 완성
//
//        System.out.println("mm.multiply(3, 5) = " + mm.multiply(3, 5));


    }
}
