package org.zzy.redline;

import java.io.Serializable;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/23 8:28
 * 描    述：
 * 修订历史：
 * ================================================
 */
class Person implements Serializable {
    private String name;
    private int age;
    private Head head;
    private int sexy;



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

    class Head{
        private String eye;
        private String ear;

        public String getEye() {
            return eye;
        }

        public void setEye(String eye) {
            this.eye = eye;
        }

        public String getEar() {
            return ear;
        }

        public void setEar(String ear) {
            this.ear = ear;
        }
    }
}
