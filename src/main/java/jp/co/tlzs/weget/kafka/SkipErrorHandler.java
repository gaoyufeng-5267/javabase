package jp.co.tlzs.weget.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerAwareErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class SkipErrorHandler implements ContainerAwareErrorHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(Exception thrownException, List<ConsumerRecord<?, ?>> records,
                       Consumer<?, ?> consumer, MessageListenerContainer container) {
        consumer.assignment().stream()
                .collect(Collectors.toMap(identity(), t -> consumer.position(t) + 1))
                .forEach(consumer::seek);
//        consumer.assignment().forEach(t -> consumer.seek(t, consumer.position(t) + 1));
        log.warn(thrownException.getMessage(), thrownException);
    }
}
