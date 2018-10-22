package zlCache.util;

import zlCache.serializer.Serializer;

/**
 * @author xier
 * @date 2018/10/7 10:38
 */
public class SerializationUtil
{
    public static <T> byte[] serialize(Serializer<T> serializer, T input) {
        return serializer.serialize(input);
    }

    public static <T> T deserialize(Serializer<T> serializer, byte[] data) {
        return serializer.deserialize(data);
    }
}
