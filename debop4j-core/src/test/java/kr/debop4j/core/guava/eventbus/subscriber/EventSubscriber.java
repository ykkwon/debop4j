package kr.debop4j.core.guava.eventbus.subscriber;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import java.util.List;

/**
 * kr.debop4j.core.guava.eventbus.subscriber.EventSubscriber
 *
 * @author 배성혁 ( sunghyouk.bae@gmail.com )
 * @since 12. 12. 10.
 */
public abstract class EventSubscriber<T> {

    List<T> events = Lists.newArrayList();

    public EventSubscriber(EventBus eventBus) {
        eventBus.register(this);
    }

    public abstract void handleEvent(T event);

    public List<T> getHandledEvents() {
        return events;
    }
}
