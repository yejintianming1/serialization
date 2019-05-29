package serializer.impl;

import com.alibaba.fastjson.JSON;
import org.apache.thrift.TBase;
import org.apache.thrift.TDeserializer;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TCompactProtocol;
import serializer.ISerializer;
import thrift.User;

/**
 * thrift进行序列化，需要编写IDL文件,跨语言，支持二进制和json数据格式
 */
public class ThriftSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof TBase)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            TSerializer serializer = new TSerializer(new TCompactProtocol.Factory());
            return serializer.serialize((TBase) obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {

        try {
            if (!TBase.class.isAssignableFrom(clazz)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            //TBinaryProtocol(二进制) TJSONProtocol(JSON) TCompactProtocol(二进制压缩)

            TBase o = (TBase) clazz.newInstance();
            TDeserializer tDeserializer = new TDeserializer(new TCompactProtocol.Factory());
            tDeserializer.deserialize(o, data);
            return (T) o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setEmail("wuyangsheng@depon.com");
        user.setName("wuyangsheng");

        //序列化
        byte[] data = new ThriftSerializer().serialize(user);
        //反序列化
        User newUser = new ThriftSerializer().deserialize(data, User.class);
        System.out.println(JSON.toJSONString(newUser));

        data = new ThriftSerializer().serialize("test");
        String test = new ThriftSerializer().deserialize(data,String.class);
        System.out.println(test);
    }
}
