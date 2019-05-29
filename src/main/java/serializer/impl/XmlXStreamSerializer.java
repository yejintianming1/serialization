package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import model.User;
import serializer.ISerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XStream序列化XML
 */
public class XmlXStreamSerializer implements ISerializer {

    private static final XStream xStream = new XStream(new DomDriver());

    @Override
    public <T> byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String xml = new String(data);
        return (T) xStream.fromXML(xml);
    }

    public static void main(String[] args) {
        User u = new User();
        u.setEmail("wuyangsheng@deppon.com");
        u.setName("wuyangsheng");

        User u1 = new User();
        u1.setEmail("1111@deppon.com");
        u1.setName("111");

        List<User> userList = new ArrayList<User>();
        Map<String, User> userMap = new HashMap<String, User>();
        userList.add(u1);
        userMap.put("a", u1);

        u.setUserList(userList);
        u.setUserMap(userMap);

        byte[] userByte = new XmlXStreamSerializer().serialize(u);
        User user = new XmlXStreamSerializer().deserialize(userByte,User.class);
        System.out.println(JSON.toJSONString(user,SerializerFeature.DisableCircularReferenceDetect));
    }
}
