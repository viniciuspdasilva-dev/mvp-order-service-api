package io.git.mvp.mvp_order_service_api.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
//    topicos existentes create_new_order_topic:1:1,cancel_order_topic:1:1,update_order_topic:1:1,result_order_success_topic:1:1,result_order_failed_topic:1:1
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(Long orderId) {
        kafkaTemplate.send("create_new_order_topic", orderId.toString());
    }
    public void cancel_order(Long orderId) {
        kafkaTemplate.send("cancel_order_topic", orderId.toString());
    }
}
