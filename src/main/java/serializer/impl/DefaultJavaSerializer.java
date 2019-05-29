package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import model.B;
import model.C;
import model.User;
import serializer.ISerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * java默认序列化
 */
public class DefaultJavaSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return  (T)objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        B b = new B();
        b.setA("1");
        b.setB("2");
        b.setC("3");
        B.setD("4");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(b);
        objectOutputStream.close();
        byteArrayOutputStream.toByteArray();

        byte[] bytes = new DefaultJavaSerializer().serialize(b);
//        String s = new String(bytes,"ISO-8859-1");
//        System.out.println(s);
//        C b1 = new ProtoStuffSerializer().deserialize(bytes,C.class);
        B b1 = new DefaultJavaSerializer().deserialize(bytes, B.class);
        System.out.println(JSON.toJSONString(b1));



        User u = new User();
        u.setEmail("wuyangsheng@deppon.com");
        u.setName("wuyangsheng");

        User u1 = new User();
        u1.setEmail("wenqinqin@deppon.com");
        u1.setName("wenqinqin");

        List<User> userList = new ArrayList<User>();
        userList.add(u1);
        Map<String, User> userMap = new HashMap<String, User>();
        userMap.put("a", u1);

        u.setUserList(userList);
        u.setUserMap(userMap);

        byte[] userByte = new DefaultJavaSerializer().serialize(u);
        User user = new DefaultJavaSerializer().deserialize(userByte,User.class);
//        System.out.println(user.getEmail() + " : " + user.getName() + " : " + u.getUserList() + " : " + u.getUserMap());
        System.out.println(JSON.toJSONString(user, SerializerFeature.DisableCircularReferenceDetect));
    }

}
