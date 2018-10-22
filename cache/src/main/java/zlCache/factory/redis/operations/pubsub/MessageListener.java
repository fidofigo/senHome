package zlCache.factory.redis.operations.pubsub;

/**
 * @author xier
 * @date 2018/10/7 10:44
 */
public interface MessageListener
{
    /**
     * Callback for processing received objects through Redis.
     *
     * @param channel channel
     * @param message message
     * @param pattern pattern matching the channel (if specified) - can be null
     */
    void onMessage(String channel, Object message, String pattern);
}
