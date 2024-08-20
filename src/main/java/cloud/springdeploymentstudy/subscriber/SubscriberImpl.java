package cloud.springdeploymentstudy.subscriber;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

@Slf4j
@Getter
public class SubscriberImpl<T> implements Subscriber<T> {

    private final Class<T> type;
    private Subscription subscription;

    public SubscriberImpl(Class<T> type) {
        this.type = type;
    }
    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void onNext(T item) {
        log.info("received = {} ", item);
    }

    @Override
    public void onError(Throwable throwable) {
        log.info("error = {} ", throwable);
    }

    @Override
    public void onComplete() {
        log.info("completed");
    }
}
