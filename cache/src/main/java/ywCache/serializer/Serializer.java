package ywCache.serializer;

/**
 * @author xier
 * @date 2018/10/7 10:27
 */
public interface Serializer<T>
{
    /**
     * @param t
     * @return
     */
    byte[] serialize(T t);

    /**
     * @param bytes
     * @return
     */
    T deserialize(byte[] bytes);
}
