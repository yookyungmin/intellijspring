package com.fastcampus.ch3.diCopy2;


import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

class Car{}
class SportsCar extends  Car{}
class Truck extends Car{}
class Engine{}


class AppContext{
    Map map; //객체 저장소

        AppContext(){
            
            //config.txt를 읽어서 Properties에 저장
            try {
                Properties p = new Properties();
                p.load(new FileReader( "config.txt"));
                //Properties 에 저장된 내용을 Map 에 저장
                map = new HashMap(p);

                //반복문으로 클래스 이름을 얻어서 객체를 생성해서 다시 map 에 저장
                for(Object key : map.keySet()){
                    //클래스 객체(설계도)를 얻어서
                    Class clazz = Class.forName((String)map.get(key)); // map에 저장된 key를 이용해 클래스 정보를 가져옴
                    map.put(key, clazz.newInstance());// 클래스 정보를 얻어서 객체 만들어 map에 저장
                }
            } catch (Exception e) {
              e.printStackTrace();
            }
        } //하드코딩
        Object getBean(String key){
            return map.get(key);
        }
        }

public class Main2 {
    public static void main(String[] args) throws Exception{
        AppContext ac = new AppContext();
        Car car = (Car)ac.getBean("car");
        Engine engine = (Engine)ac.getBean("engine");

        System.out.println("car="+car);
        System.out.println("Engine="+engine);

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