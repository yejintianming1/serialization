package serializer.impl;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.InvalidProtocolBufferException;
import model.AddressBookProtos;
import org.apache.commons.lang3.reflect.MethodUtils;
import serializer.ISerializer;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * protobuf 是Google的一种数据交换格式，它独立于语言，独立于平台
 */
public class ProtoBufSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T obj) {
        try {
            if (!(obj instanceof GeneratedMessageV3)) {
                throw new UnsupportedOperationException("not supported obj type");
            }
            return (byte[]) MethodUtils.invokeMethod(obj, "toByteArray");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        try {
            if (!GeneratedMessageV3.class.isAssignableFrom(clazz)) {
                throw new UnsupportedOperationException("not supported obj type");
            }

            Object o = MethodUtils.invokeStaticMethod(clazz, "getDefaultInstance");
            return (T) MethodUtils.invokeMethod(o, "parseFrom", new Object[]{data});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //构建一个Person对象
        AddressBookProtos.Person person = AddressBookProtos.Person
                .newBuilder()
                .setEmail("wuyangsheng@deppon.com")
                .setId(1000)
                .setName("wuyangsheng")
                .addPhone(
                        AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("18297787572")
                                .setType(AddressBookProtos.Person.PhoneType.HOME).build())
                .build();

        //序列化
        System.out.println(person.toByteString());
        System.out.println(Arrays.toString(person.toByteArray()));

        //反序列化方法一
        AddressBookProtos.Person newPerson = null;
        try {
            newPerson = AddressBookProtos.Person.parseFrom(person.toByteString());
            System.out.println(newPerson);
            //反序列化方法二
            newPerson = AddressBookProtos.Person.parseFrom(person.toByteArray());
            System.out.println(newPerson);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 向地址簿添加两条Person信息
        AddressBookProtos.AddressBook.Builder books = AddressBookProtos.AddressBook.newBuilder();
        books.addPerson(person);
        books.addPerson(AddressBookProtos.Person.newBuilder(person).setEmail("wuyangsheng@deppon.com").build());
        System.out.println(books.build());

        System.out.println("-------------------------------------------------");
        byte[] data = new ProtoBufSerializer().serialize(person);
        AddressBookProtos.Person personCopy = new ProtoBufSerializer().deserialize(data, AddressBookProtos.Person.class);
        System.out.println(personCopy);



    }
}
