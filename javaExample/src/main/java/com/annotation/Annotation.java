package com.annotation;

import java.lang.annotation.*;

/**
 * Created by zhouhaiming on 2017-5-10 20:32
 * Email: dg_chow@163.com
 *
 * @Description: 注解的使用
 */
public class Annotation {
    // 定义Description注解
    // @Target和@Retention。@Target用来定义你的注解将用于什么地方（是一个方法上还是一个类上），@Retention用来定义该注解在哪一个级别上可用（在源代码上或者是类文件上或者是运行时）
    @Target({ElementType.FIELD,ElementType.METHOD})// 方法声明
    @Retention(RetentionPolicy.RUNTIME)//	VM将在运行期也保留注解，因此可以通过反射机制读取注解的信息
    @Inherited // 允许子类继承父类中的注解
    @Documented//将此注解包含在Javadoc中
    // 使用@interface 关键字定义注解
    public @interface Description{
        // 成员以无参无异常方式声明
        String desc();
        String author();
        String time();
        // 可以使用default关键字为成员指定一个默认值
        int age() default 18;
    }



}
