package ywCache.factory.redis.operations.pubsub;

import redis.clients.jedis.BinaryJedisPubSub;

/**
 * @author xier
 * @date 2018/10/7 10:45
 */
public class Subscription
{
    private BinaryJedisPubSub binaryJedisPubSub;
    private MessageListener messageListener;

    public Subscription(MessageListener messageListener)
    {
        this.messageListener = messageListener;
        this.binaryJedisPubSub = null;
    }

    public BinaryJedisPubSub getBinaryJedisPubSub()
    {
        return binaryJedisPubSub;
    }

    public void storeBinaryJedisPubSub(BinaryJedisPubSub binaryJedisPubSub)
    {
        this.binaryJedisPubSub = binaryJedisPubSub;
    }

    public MessageListener getMessageListener()
    {
        return messageListener;
    }
}
