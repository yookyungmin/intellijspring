package com.fastcampus.ch3;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

//@Component
@Component class Engine{} //("egnine") class 앞에 생략됨 // bean태그에 <bean id = "engine" class = "com.fastcampus.ch3.Engine"/>
@Component class SuperEngine extends Engine {}
@Component class TurboEngine extends  Engine{}
@Component class Door{}
@Component class Car{
    @Value("red") String color;
    @Value("100") int oil;
//    @Autowired //bytype  Resource 보다 Autowired로 많이 쓰는이유는 이름이 바뀔수 있고 타입은 잘안바뀌떄문에
//    @Qualifier("superEngine") //
//    @Resource(name="superEngine") //byname
    @Autowired Engine engine;  //bytype -타입으로 먼저 검색, 여러개면 이름으로 검색, - engine, superEngine, turboEngine
    @Autowired  Door[] doors; //배열
    public Car(){} //기본생성자를 꼭 만들어주자

    public Car(String color, int oil, Engine engine, Door[] doors) {
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getOil() {
        return oil;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Door[] getDoors() {
        return doors;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }
    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }

}
public class SpringDiTest {
    public static void main(String[] args) {

        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
        //Car car = (Car)ac.getBean("car"); //byname 아래와 같은문장
        Car car = ac.getBean("car", Car.class); //byname, byType같이 쓰면 형변환 안해도됨
//        Car car2 = (Car)ac.getBean(Car.class); //bytype car car2 기본적으로 싱글톤이라서 같은객체


        Engine engine =(Engine) ac.getBean("engine");//byName
        //Engine engine =(Engine)ac.getBean(Engine.class); //bytype - 같은 타입이 3개라서 에러
        Door door = (Door)ac.getBean("door");


//        car.setColor("red"); //기본형이나 String일댄 value
//        car.setOil(100);  //정수여도 xml에선 " " 붙여야됨
//        car.setEngine(engine); //참조형 이기에 ref
//        car.setDoors(new Door[]{ac.getBean("door",Door.class), (Door)ac.getBean("door")});
        System.out.println("Car="+car);
//        System.out.println("engine="+engine);

    }
}

//getBean 할떄 기본적으로  Singleton이라 여러번 호출해도 같은 객체로 생성되는데
/*
*   <bean id = "car" class="com.fastcampus.ch3.Car" scope = "prototype"/> 겟빈할떄마다 새로운 객체를 생성하길원하면 protoType
    <bean id = "engine" class="com.fastcampus.ch3.Engine" scope="singleton"/>*/