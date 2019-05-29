package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import model.FDateJsonDeserializer;
import model.FDateJsonSerializer;
import model.User;
import serializer.ISerializer;

import java.util.*;

/**
 * Jackson序列化JSON数据
 */
public class JSONJacksonSerializer implements ISerializer {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        SimpleModule module = new SimpleModule("DateTimeModule", Version.unknownVersion());
        module.addSerializer(Date.class, new FDateJsonSerializer());
        module.addDeserializer(Date.class, new FDateJsonDeserializer());

        objectMapper.registerModule(module);
    }

    private static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }

    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            return new byte[0];
        }

        try {
            String json = objectMapper.writeValueAsString(obj);
            return json.getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        String json = new String(data);
        try {
            return (T)objectMapper.readValue(json,clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User u = new User();
        u.setEmail("wenqinqin@deppon.com");
        u.setName("wenqinqin");

        User u1 = new User();
        u1.setEmail("wuyangsheng@deppon.com");
        u1.setName("wuyangsheng");

        List<User> userList = new ArrayList<>();
        Map<String, User> userMap = new HashMap<>();
        userList.add(u1);
        userMap.put("a", u1);

        u.setUserList(userList);
        u.setUserMap(userMap);

        byte[] userByte = new JSONJacksonSerializer().serialize(u);
        User user = new JSONJacksonSerializer().deserialize(userByte,User.class);
        System.out.println(JSON.toJSONString(user,SerializerFeature.DisableCircularReferenceDetect));
    }
}
