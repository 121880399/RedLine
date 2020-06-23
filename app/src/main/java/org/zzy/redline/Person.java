package org.zzy.redline;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/23 8:28
 * 描    述：
 * 修订历史：
 * ================================================
 */
class Person {
    private String name;
    private int age;

    public Person(){

    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
