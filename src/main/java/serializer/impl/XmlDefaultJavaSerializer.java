package serializer.impl;

import com.alibaba.fastjson.JSON;
import model.User;
import serializer.ISerializer;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * java自带的XML序列化
 */
public class XmlDefaultJavaSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        XMLEncoder xe  = new XMLEncoder(out, "utf-8", true, 0);
        xe.writeObject(obj);
        xe.close();
        return out.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        XMLDecoder xd = new XMLDecoder(new ByteArrayInputStream(data));
        Object obj = xd.readObject();
        xd.close();
        return (T) obj;
    }


    public static void main(String[] args) {
        User u = new User();
        u.setEmail("wuyangsheng@deppon.com");
        u.setName("wuyangsheng");

        XmlDefaultJavaSerializer serializer = new XmlDefaultJavaSerializer();
        byte[] bytes = serializer.serialize(u);
        User u1 = serializer.deserialize(bytes, User.class);
        System.out.println(JSON.toJSONString(u1));
    }
}
