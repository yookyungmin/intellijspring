package com.fastcampus.ch3.diCopy1;


import java.io.FileReader;
import java.util.Properties;

class Car{}
class SportsCar extends  Car{}
class Truck extends Car{}
class Engine{}


public class Main1 {
    public static void main(String[] args) throws Exception{
//        Car car = getCar();
        Car car = (Car)getObject("car");
        Engine engine = (Engine)getObject("engine");

        System.out.println("car="+car);
        System.out.println("Engine="+engine);

    }

    static Object getObject(String key) throws Exception{
        //config.txt를 읽어서 Properties에 저장
        Properties p = new Properties();
        p.load(new FileReader( "config.txt"));

        //클래스 객체(설계도)를 얻어서
        Class clazz = Class.forName(p.getProperty(key)); // key 가 car인 엔트리의 value를 얻어오는것

        return clazz.newInstance(); //객체를 생성해서 반환

    }
    static Car getCar() throws Exception{
        //config.txt를 읽어서 Properties에 저장
        Properties p = new Properties();
        p.load(new FileReader( "config.txt"));

        //클래스 객체(설계도)를 얻어서
        Class clazz = Class.forName(p.getProperty("car")); // key 가 car인 엔트리의 value를 얻어오는것

        return (Car)clazz.newInstance(); //객체를 생성해서 반환

    }
}
//변경에 유리한 코드를 작성하기 위해 만듬
//config.txt 
/*car=com.fastcampus.ch3.diCopy1.SportsCar
engine=com.fastcampus.ch3.diCopy1.Engine

Key = car, engine
com.fastcampus.ch3.diCopy1.Engine = 어떤 클래스의 객체를 생성해서 쓰게 할건지를 정해줌 경로
* 
* */