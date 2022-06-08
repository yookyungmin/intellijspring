package com.fastcampus.ch3.diCopy4;


import com.google.common.reflect.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component class Car{
//     @Autowired
//     Engine engine;
//       @Autowired Door door;

    @Resource  Engine engine;
   Door door;

    @Override
    public String toString() {
        return "Car{" +
                "engine=" + engine +
                ", door=" + door +
                '}';
    }
}
@Component class SportsCar extends  Car{}
@Component class Truck extends Car{}
@Component
class Engine{} //Component 없으면 null 값나옴

@Component class Door{}

class AppContext{
    Map map; //객체 저장소

        AppContext(){
            map = new HashMap();
            doComponentScan();
            doAutowired();
            doResource();
        }

    private void doResource() {
        //map 에 저장된 객체의 iv중에 @Resource가 붙어있으면
        //map에서 iv의 이름에 맞는 객체를 찾아서 연결(객체의 주소를 iv저장)
        try {
            for(Object bean: map.values()){
                for(Field fld :bean.getClass().getDeclaredFields())
                {
                    if (fld.getAnnotation(Resource.class) != null); // 클래스가 갖고 있는 플드중에서 Resource가 붙어있으면 byName 이름으로 찾기
                    fld.set(bean, getBean(fld.getName())); // car.engine = obj; car에 엔진이 있으면 obj를 넣어줌
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doAutowired() {
            //map 에 저장된 객체의 iv중에 @Autowired가 붙어있으면
            //map에서 iv타입의 맞는 객체를 찾아서 연결(객체의 주소를 iv저장)
        try {
            for(Object bean: map.values()){
                for(Field fld :bean.getClass().getDeclaredFields())
                {
                    if (fld.getAnnotation(Autowired.class) != null); // 클래스가 갖고 있는 플드중에서 Autowired가 붙어있으면 byType 타입으로 찾기
                    fld.set(bean, getBean(fld.getType())); // car.engine = obj; car에 엔진이 있으면 obj를 넣어줌
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doComponentScan() {
        try {
            //1패키지 내의 클래스목록을 가져온다
            //2. 반복문으로 클래스를 하나식 읽어와서 @component 이 붙어 있는지 확인
            //3 @Component 가 붙어 잇으면 객체를 생성해서 map에 저장
            ClassLoader classLoader = AppContext.class.getClassLoader(); //클래스 목록을 가져오려면
            ClassPath classPath = ClassPath.from(classLoader); 

           Set<ClassPath.ClassInfo> set =  classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy4");

            for(ClassPath.ClassInfo classInfo : set){ //반복문으로 담긴것을 하나씩 읽어옴 //패키지 내에 @Component 붙은 클래스 찾기
                Class clazz = classInfo.load(); //클래스 정보 얻어옴
               Component component =(Component)clazz.getAnnotation(Component.class); //클래스가 Component 에노테이션 있는지 확인
               if(component != null){//Component 에노테이션이 붙어있는 클래스면 
                  String id = StringUtils.uncapitalize(classInfo.getSimpleName()); //첫글자를 대문자로 바꿔서 map에 객체를만들고 저장
                  map.put(id, clazz.newInstance()); //map에 객체를만들고 저장
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key){
            return map.get(key);
        }//byname 이름으로 객체 찾기
       
    Object getBean(Class clazz){//bytype 타입으로 객체찾기
            for(Object obj : map.values()){
                if(clazz.isInstance(obj)) //value 지정된 clazz 타입의 인스턴스면 value 반환
                    return  obj;
            }

            return null;
       }
       
        }

public class Main4 {
    public static void main(String[] args) throws Exception{
        AppContext ac = new AppContext();
        Car car = (Car)ac.getBean("car"); //byname 으로 객체검색
        Engine engine = (Engine)ac.getBean("engine");
        Door door = (Door)ac.getBean(Door.class);//bytype으로 객체 검색
        
        //수동으로 객체 연결
//        car.engine = engine;
//        car.door = door;
        //iv 앞에 @AutoWired나 @Resource 붙어있으면 수동객체 연결안해도됨
        
        System.out.println("car="+car);
        System.out.println("Engine="+engine);
        System.out.println("Door="+door);

        System.out.println("에런가"+door);

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