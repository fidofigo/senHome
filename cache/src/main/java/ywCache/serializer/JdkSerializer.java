package ywCache.serializer;

import ywCache.exception.SerializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author xier
 * @date 2018/10/7 10:36
 */
public class JdkSerializer implements Serializer<Object>
{
    private static final Logger log = LoggerFactory.getLogger(JdkSerializer.class);

    /***
     * 序列化
     *
     * @param input
     * @return
     */
    @Override
    public byte[] serialize(Object input) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(input);
            oos.flush();
            return bos.toByteArray();
        } catch (Exception e) {
            throw new SerializationException(e);
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /***
     * 反序列化
     *
     * @param data
     * @return Object
     */
    @Override
    public Object deserialize(byte[] data) {
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(data);
            ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (Exception e) {
            throw new SerializationException(e);
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
