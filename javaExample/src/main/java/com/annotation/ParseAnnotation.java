package com.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by zhouhaiming on 2017-5-11 9:00
 * Email: dg_chow@163.com
 *
 * @Description: 解析注解
 */
public class ParseAnnotation {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class<?> class1 = null;
        Student student= null;
        try {
            // 使用类加载器加载类
            class1 = Class.forName("com.annotation.Student");
             student= (Student)class1.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        // 判断Student【类上】是否有Description注解
        boolean isExits = class1.isAnnotationPresent(Annotation.Description.class);
        if(isExits){
            // 注解实例
            Annotation.Description desc = class1.getAnnotation(Annotation.Description.class);
            System.out.println("注解：" + desc.toString());
        }//if

        // 获取Student类上的所有方法
        Method[] methods = class1.getMethods();
        // 遍历所有方法
        for (Method method : methods) {
            // 判断方法上是否有Description注解
            isExits = method.isAnnotationPresent(Annotation.Description.class);
            if(isExits){
                Annotation.Description description = method.getAnnotation(Annotation.Description.class);
                System.out.println("方法注解：" + description.toString());
                //获取注解上的值
                String author= description.author();
                System.out.println(author);
                //通过反射调用方法
                method.invoke(student,author);
            }//if
        }//for
        System.out.println("验证反射调用方法是否成功："+student.getName());

        //获取所有的字段
        Field[] fields =class1.getDeclaredFields();
        //遍历所有的字段
        for (Field field : fields) {
            // 判断字段上是否有Description注解
            isExits = field.isAnnotationPresent(Annotation.Description.class);
            if (isExits) {
                Annotation.Description description = field.getAnnotation(Annotation.Description.class);
                System.out.println("字段注解：" + description.toString());
                //获取注解上的值
                String author= description.author();
                System.out.println(author);
                //通过反射设置字段的值
                field.setAccessible(true);//Student类中的成员变量为private,故必须进行此操作
                field.set(student,author);
            }
        }
        System.out.println("验证反射设置字段的值是否成功："+student.getName());
    }
}
