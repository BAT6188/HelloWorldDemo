package com.annotation;

/**
 * Created by zhouhaiming on 2017-5-11 8:54
 * Email: dg_chow@163.com
 *
 * @Description:使用注解
 */

//语法：@<注解名称>(<成员名1> = <成员值1>，<成员名2> = <成员值2>，...)
public class Student {
    @Annotation.Description(desc = "get name from student object" , author = "新的名称：小王", time = "2016-01-11")
    private String name;

//    @Annotation.Description(desc = "set name for student object", author = "sjf0115", time ="2016-01-11" )
    public String getName() {
        return name;
    }

    @Annotation.Description(desc = "get name from student object" , author = "名称：小明", time = "2016-01-11")
    public void setName(String name) {
        this.name = name;
    }
}
