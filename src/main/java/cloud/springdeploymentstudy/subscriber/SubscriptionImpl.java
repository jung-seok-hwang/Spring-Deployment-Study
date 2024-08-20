package cloud.springdeploymentstudy.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Random;


@Slf4j
public class SubscriptionImpl<T> implements Subscription {

    private Subscriber<? super T> subscriber;
    private boolean isCancelled;
    private static final int MAX_ITEMS = 10;
    private int count = 0;

    public SubscriptionImpl(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override
    public void request(long requested) {
        if(isCancelled){
            return;
        }
        log.info("subscriber has requested {} items", requested);
        if(requested > MAX_ITEMS){
            this.subscriber.onError(new RuntimeException("validation failed"));
            this.isCancelled = true;
            return;
        }
        for (int i = 0; i < requested && count < MAX_ITEMS; i++) {
            count++;
            this.subscriber.onNext(generateItem());
        }
        if(count == MAX_ITEMS){
            log.info("no more data to produce");
            this.subscriber.onComplete();
            this.isCancelled = true;
        }
    }

    @Override
    public void cancel() {
        log.info("Subscription has cancelled");
        this.isCancelled = true;
    }

    // 제네릭 T 타입 아이템 생성
    private T generateItem() {
        if (subscriber instanceof SubscriberImpl<?>) {
            var type = ((SubscriberImpl<?>) subscriber).getType();

            if (type.equals(String.class)) {
                String username = generateRandomString(8);
                String domain = generateRandomDomain();
                return (T) (username + "@" + domain);
            } else if (type.equals(Integer.class)) {
                return (T) Integer.valueOf(generateRandomInteger());  // Integer 타입으로 캐스팅
            }
        }
        throw new ClassCastException("The subscriber is not of type SubscriberImpl.");
    }

    private String generateRandomDomain() {
        var list = List.of("example.com", "gmail.com");
        return list.get(new Random().nextInt(list.size()));
    }

    private String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    private int generateRandomInteger() {
        return new Random().nextInt(100);
    }
}
