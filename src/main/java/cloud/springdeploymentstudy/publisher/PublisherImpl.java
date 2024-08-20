package cloud.springdeploymentstudy.publisher;

import cloud.springdeploymentstudy.subscriber.SubscriptionImpl;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

public class PublisherImpl<T> implements Publisher<T> {

    @Override
    public void subscribe(Subscriber<? super T> subscriber) {
        var subscription = new SubscriptionImpl<>(subscriber);
        subscriber.onSubscribe(subscription);
    }
}
