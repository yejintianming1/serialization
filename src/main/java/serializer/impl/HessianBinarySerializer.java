package serializer.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import model.User;
import model.UserAp;
import serializer.ISerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hessian序列化二进制数据
 */
public class HessianBinarySerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        if (obj == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            HessianOutput ho = new HessianOutput(os);
            ho.writeObject(obj);
            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        if (data == null) {
            return null;
        }
        try {
            ByteArrayInputStream is = new ByteArrayInputStream(data);
            HessianInput hi = new HessianInput(is);
            return (T) hi.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        User u = new User();
        u.setEmail("wuyangsheng@deppon.com");
        u.setName("wuyangsheng");

        User u1 = new User();
        u1.setEmail("wenqinqin@deppon.com");
        u1.setName("wenqinqin");

        List<User> userList = new ArrayList<User>();
        Map<String, User> userMap = new HashMap<String, User>();
        userList.add(u1);
        userMap.put("a", u1);

        u.setUserList(userList);
        u.setUserMap(userMap);

        String ustr = JSON.toJSONString(u);

        byte[] userByte = new HessianBinarySerializer().serialize(ustr);

        String messageObject = new String(userByte,"UTF-8").trim();

        System.out.println(messageObject);

        String user = new HessianBinarySerializer().deserialize(userByte, String.class);

        System.out.println(user);
        UserAp userAp = JSONObject.parseObject(user, UserAp.class);
        System.out.println(JSON.toJSONString(userAp));


//        System.out.println(JSON.toJSONString(user,SerializerFeature.DisableCircularReferenceDetect));

    }
}
