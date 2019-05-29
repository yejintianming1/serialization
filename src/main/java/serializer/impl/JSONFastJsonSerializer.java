package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import model.User;
import org.apache.commons.collections.CollectionUtils;
import serializer.ISerializer;

/**
 * FastJson序列化JSON数据
 */
public class JSONFastJsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        return JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T) JSON.parseObject(new String(data), clazz);
    }

    public static void main(String[] args) {
        User u = new User();
        u.setEmail("wenqinqin@deppon.com");
        u.setName("wenqinqin");

        JSONFastJsonSerializer serializer = new JSONFastJsonSerializer();
        byte[] bytes = serializer.serialize(u);
        User u1 = serializer.deserialize(bytes, User.class);
        System.out.println(JSON.toJSONString(u1));
    }
}
