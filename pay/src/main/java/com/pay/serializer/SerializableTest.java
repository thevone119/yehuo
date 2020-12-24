package com.pay.serializer;


import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    public byte[] toBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(intToByteArray(id));
        byte[] names=name.getBytes();
        byteArrayOutputStream.write(intToByteArray(names.length));
        byteArrayOutputStream.write(names);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * int到byte[] 由高位到低位
     * @param i 需要转换为byte数组的整行值。
     * @return byte数组
     */
    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }

    @Test
    public void output() throws IOException {
        //100万次
        int testTime = 10000*1000;
        long sersite = 0;
        int testtype=1;
        if (testtype==0){
            //10万次序列化,
            for (int i = 0; i < testTime; i++) {
                SerializableTest user = new SerializableTest();
                user.setId(1000000);
                user.setName("FileNotFoundException"+i);
                byte[] bytes = KryoUtil.writeObjectToByteArray(user);
                sersite+=bytes.length;
            }
        }

        if (testtype==1){
            for (int i = 0; i < testTime; i++) {
                SerializableTest user = new SerializableTest();
                user.setId(1000000);
                user.setName("FileNotFoundException"+i);
                byte[] bytes = user.toBytes();
                sersite+=bytes.length;

            }
        }

        if (testtype==2){
            for (int i = 0; i < testTime; i++) {
                SerializableTest user = new SerializableTest();
                user.setId(1000000);
                user.setName("FileNotFoundException"+i);
                byte[] bytes = JSONObject.toJSONBytes(user);;
                sersite+=bytes.length;
            }
        }


        System.out.println("sersite:"+sersite);

    }



}
