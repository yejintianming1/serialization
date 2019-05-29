package serializer;

import org.apache.commons.lang.StringUtils;

public enum SerializeType {

    AveroSerializer("AvroSerializer"),
    DefaultJavaSerializer("DefaultJavaSerializer"),
    HessianBinarySerializer("HessianBinarySerializer"),
    JSONFastJsonSerializer("JSONFastJsonSerializer"),
    JSONGSONSerializer("JSONGSONSerializer"),
    JSONJackonSerializer("JSONJacksonSerializer"),
    MarshallingSerializer("MarshallingSerializer"),
    ProtoBufSerializer("ProtoBufSerializer"),
    ProtoStuffSerializer("ProtoStuffSerializer"),
    ThriftSerializer("ThriftSerializer"),
    XmlDefaultJavaSerializer("XmlDefaultJavaSerializer"),
    XmlXStreamSerializer("XmlXStreamSerializer")
    ;


    private String serializeType;

    private SerializeType(String serializeType) {
        this.serializeType = serializeType;
    }

    public static SerializeType queryByType(String serializeType) {
        if (StringUtils.isBlank(serializeType)) {
            return null;
        }
        for (SerializeType serialize: SerializeType.values()) {
            if (StringUtils.equals(serializeType, serialize.getSerializeType())) {
                return serialize;
            }
        }
        return null;
    }

    public String getSerializeType() {
        return serializeType;
    }
}
