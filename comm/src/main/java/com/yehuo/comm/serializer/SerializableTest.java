package com.yehuo.comm.serializer;


import org.junit.Test;

import java.io.FileNotFoundException;

public class SerializableTest {
    private Integer id;
    private String name;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + "]";
    }

    @Test
    public void output() throws FileNotFoundException {

        Serializer ser = new kryoSerializer(SerializableTest.class);
        long sersite=0;
        //10万次序列化
        for (int i = 0; i < 10000*10; i++) {
            SerializableTest user = new SerializableTest();
            user.setId(100);
            user.setName("FileNotFoundException"+i);
            byte[] bytes = new byte[300];
            ser.serialize(user, bytes);
            sersite+=bytes.length;
        }
        System.out.println("sersite:"+sersite);

    }



}
