package com.fastcampus.ch3.aop;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Aop 핵심기능, 부가기능 메서드 분리
//빨간줄 생성 시 예외 발생할수 잇어서 생길수있다
public class AopMain {
    public static void main(String[] args) throws Exception {

        
    MyAdvice myAdvice = new MyAdvice();
    
    Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass"); //MyClass 클래스의 객체를얻어옴
    Object obj = myClass.newInstance(); //클래스 객체로부터 객체를 생성
        
        //반복문을 이용하여 MyClass에 있는 메서드를 하나식 호출
        for(Method m:myClass.getDeclaredMethods()){ //MyClass에 정의되어잇는 메소드를 배열로 가져와 하나씩 꺼내 반복문돔
            myAdvice.invoke(m, obj, null);//MyAdvice invoke 메서드 사용
        }
    }
}
//매개변수 안에 Object.. args 동일한 파라미터를 여러개, 멘마지막에사용
class MyAdvice{
    Pattern p = Pattern.compile("a.*"); //일부 메서드에만 추가 하고싶으면 사용, a로 시작되는메서드 //regex 정규식이용
    boolean matches(Method m){
        Matcher matcher = p.matcher(m.getName());  //Matcher= 패턴 클래스를 받아 대상 문자열과 패턴이 일치하는 부분을 찾거나 판별
        return matcher.matches();
    }
    void invoke(Method m, Object obj, Object... args)throws Exception{ //MyClass 메서드내에 들어갈코드 추가 Object.. args
        if(matches(m))// 메서드의 이름이 패턴하고 일치하는 경우만 문장 실행
        System.out.println("[before]{");
        
        m.invoke(obj, args); //aaa() , aaa2(), bbb() 호출가능

        if(matches(m))
        System.out.println("}[after]");
    }
}
class MyClass{  //동적으로생성해서 MyAdvice, invoke 메서드로 넘겨줄예정


    void aaa(){
        System.out.println("aaa() is called");
    }

    void aaa2(){
        System.out.println("aaa2() is called");
    }

    void bbb(){
        System.out.println("bbb() is called");
    }
}
