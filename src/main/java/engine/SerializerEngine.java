package engine;

import com.google.common.collect.Maps;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.SimpleDerivation;
import model.User;
import serializer.ISerializer;
import serializer.SerializeType;
import serializer.impl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializerEngine {

    public static final Map<SerializeType, ISerializer> serializerMap = Maps.newConcurrentMap();

    //注册序列化工具类到serializerMap
    static {
        serializerMap.put(SerializeType.DefaultJavaSerializer, new DefaultJavaSerializer());
        serializerMap.put(SerializeType.HessianBinarySerializer, new HessianBinarySerializer());
        serializerMap.put(SerializeType.JSONFastJsonSerializer, new JSONFastJsonSerializer());
        serializerMap.put(SerializeType.JSONGSONSerializer, new JSONGSONSerializer());
        serializerMap.put(SerializeType.JSONJackonSerializer, new JSONJacksonSerializer());
        serializerMap.put(SerializeType.MarshallingSerializer, new MarshallingSerializer());
        serializerMap.put(SerializeType.ProtoStuffSerializer, new ProtoStuffSerializer());
        serializerMap.put(SerializeType.XmlDefaultJavaSerializer, new XmlDefaultJavaSerializer());
        serializerMap.put(SerializeType.XmlXStreamSerializer, new XmlXStreamSerializer());


        //以下不能使用普通的java bean,需要根据各自IDL(接口描述文件)编译生成的类
        serializerMap.put(SerializeType.AveroSerializer, new AvroSerializer());
        serializerMap.put(SerializeType.ThriftSerializer, new ThriftSerializer());
        serializerMap.put(SerializeType.ProtoBufSerializer, new ProtoBufSerializer());
    }

    /**
     * 序列化
     * @param obj
     * @param serializeType
     * @param <T>
     * @return
     */
    public static <T> byte[] serialize(T obj, String serializeType) {
        SerializeType serialize = SerializeType.queryByType(serializeType);
        if (serialize == null) {
            throw new RuntimeException("serialize is null");
        }

        ISerializer serializer = serializerMap.get(serialize);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }

        try {
            return serializer.serialize(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T deserialize(byte[] data, Class<T> clazz, String serializeType) {

        SerializeType serialize = SerializeType.queryByType(serializeType);
        if (serialize == null) {
            throw new RuntimeException("serialize is null");
        }
        ISerializer serializer = serializerMap.get(serialize);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }

        try {
            return serializer.deserialize(data, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User u = new User();
        u.setEmail("wuyangsheng@deppon.com");
        u.setName("wuyangsheng");

        User u1 = new User();
        u1.setEmail("wenqinqin@deppon.com");
        u1.setName("wenqinqin");

        List<User> userList = new ArrayList<>();
        Map<String,User> userMap = new HashMap<>();
        userList.add(u1);
        userMap.put("a", u1);

        u.setUserList(userList);
        u.setUserMap(userMap);

        for (Map.Entry<SerializeType, ISerializer> entry : serializerMap.entrySet()) {
            byte[] userByte = SerializerEngine.serialize(u, entry.getKey().getSerializeType());
            User user = SerializerEngine.deserialize(userByte, User.class, entry.getKey().getSerializeType());
            System.out.println(user.getEmail() + " : " + user.getName() + " : " + new String(new JSONJacksonSerializer().serialize(u.getUserList())) + " : " + new String(new JSONJacksonSerializer().serialize(u.getUserMap())));
            System.out.println("----------------------------------------------------------------");
        }
    }
}
