package jp.co.tlzs.weget.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ContainerAwareBatchErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public class SkipBatchErrorHandler implements ContainerAwareBatchErrorHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(Exception thrownException, ConsumerRecords<?, ?> data, Consumer<?, ?> consumer, MessageListenerContainer container) {
        consumer.assignment().stream()
                .collect(Collectors.toMap(identity(), t -> consumer.position(t) + 1))
                .forEach(consumer::seek);
        log.warn(thrownException.getMessage(), thrownException);
    }
}
