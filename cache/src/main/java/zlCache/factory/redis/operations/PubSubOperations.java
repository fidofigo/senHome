package zlCache.factory.redis.operations;

import zlCache.factory.redis.operations.pubsub.Subscription;

/**
 * @author xier
 * @date 2018/10/7 10:43
 */
public interface PubSubOperations
{
    /**
     * Publishes the given message to the given channel.
     *
     * @param channel the channel to publish to
     * @param message message to publish
     * @return the number of clients that received the message
     */
    Long publish(String channel, Object message);

    /**
     * Subscribes the connection to the given channels. Once subscribed, a connection enters listening mode and can only
     * subscribe to other channels or unsubscribe. No other commands are accepted until the connection is unsubscribed.
     * <p>
     * Note that this operation is blocking and the current thread starts waiting for new messages immediately.
     *
     * @param subscription subscription
     * @param channels channel names
     */
    void subscribe(Subscription subscription, String... channels);

    /**
     * Subscribes the connection to all channels matching the given patterns. Once subscribed, a connection enters
     * listening mode and can only subcribe to other channels or unsubscribe. No other commands are accepted until the
     * connection is unsubscribed.
     * <p>
     * Note that this operation is blocking and the current thread starts waiting for new messages immediately.
     *
     * @param subscription subscription
     * @param patterns channel name patterns
     */
    void pSubscribe(Subscription subscription, String... patterns);

    /**
     * Cancels the current subscription for all given channels.
     *
     * @param channels channel names
     */
    void unsubscribe(Subscription subscription, String... channels);


    /**
     * Cancels the subscription for all channels matching the given patterns.
     *
     * @param patterns
     */
    void pUnsubscribe(Subscription subscription, String... patterns);
}
