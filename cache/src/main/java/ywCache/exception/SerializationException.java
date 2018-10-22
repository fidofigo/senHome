package ywCache.exception;

/**
 * @author xier
 * @date 2018/10/7 10:37
 */
public class SerializationException extends RuntimeException
{
    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public SerializationException(String msg) {
        super(msg);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
