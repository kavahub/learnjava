package io.github.kavahub.learnjava.annotations;

import java.util.Date;

/**
 * 人员实体，{@link PropertyBuilder} 注解
 *  
 * @author PinWei Wan
 * @since 1.0.1
 */
public class Person {
    private int age;

    private String name;

    private Date birthday;

    public int getAge() {
        return age;
    }

    @PropertyBuilder
    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    @PropertyBuilder
    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    
}
