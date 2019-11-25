package test;

import java.util.Scanner;

/**
 * @Auther: MGL
 * @Date: 2019/4/29 17:33
 * @Description:
 */
abstract class Pet{
    String name;
    String sex;
    Pet(String name,String sex){}
    abstract void talk();
    abstract void eat();
}
class Dog extends Pet{
    String name;
    private String color;
    public Dog(String name,String sex,String color)
    {
        super(name,sex);
        this.name=name;
        System.out.print("名称："+name+"，性别"+sex+"，颜色："+color+",");
    }
    void talk()
    {
        System.out.print("汪汪叫\n");
    }
    void eat()
    {
        System.out.println(name+"吃骨头！");
    }
}
class Cat extends Pet{
    String name;
    private double weight;
    Cat(String name,String sex,double weight)
    {
        super(name,sex);
        this.name=name;
        System.out.print("名称："+name+"，性别"+sex+"，体重："+weight+"kg,");
    }
    void talk()
    {
        System.out.print("喵喵叫\n");
    }
    void eat()
    {
        System.out.println(name+"吃鱼！");
    }
}
public class TestMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dogName = sc.next();
        String dogSex = sc.next();
        String dogColor = sc.next();
        String catName = sc.next();
        String catSex = sc.next();
        double catWeight = sc.nextDouble();
        Dog d=new Dog(dogName,dogSex,dogColor);
        d.talk();
        d.eat();
        Cat c=new Cat(catName,catSex,catWeight);
        c.talk();
        c.eat();;
    }
}