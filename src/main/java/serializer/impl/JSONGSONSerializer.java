package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import model.User;
import serializer.ISerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GSon序列化JSON数据
 */
public class JSONGSONSerializer implements ISerializer {

    private static final Gson gson = new Gson();

    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            return new byte[0];
        }
        return gson.toJson(obj).getBytes();

    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return (T)gson.fromJson(new String(data), clazz);
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

        byte[] userByte = new JSONGSONSerializer().serialize(u);
        User user = new JSONGSONSerializer().deserialize(userByte, User.class);
        System.out.println(JSON.toJSONString(user));



    }
}
