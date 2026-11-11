package com.example.linkedInApp.notification_service.consumer;
import com.example.linkedInApp.connection_service.events.AcceptConnectionRequestEvent;
import com.example.linkedInApp.connection_service.events.SendConnectionRequestEvent;
import com.example.linkedInApp.notification_service.service.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceConsumer {


    private final SendNotification sendNotification;

    @KafkaListener(
            topics = "send-connection-request-topic",
            properties = "spring.json.value.default.type=com.example.linkedInApp.connection_service.events.SendConnectionRequestEvent")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent){
        log.info("Processing connection request notification for receiverId={}",
                sendConnectionRequestEvent.getReceiverId());

        String message = String.format(
                "You have received a connection request from user id %d",
                sendConnectionRequestEvent.getSenderId()
        );
        sendNotification.send(sendConnectionRequestEvent.getReceiverId(),message);
    }

    @KafkaListener(
            topics = "accept-connection-request-topic",
            properties = "spring.json.value.default.type=com.example.linkedInApp.connection_service.events.AcceptConnectionRequestEvent")
    public void handleAcceptConnectionRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent){
        log.info("Accepting connection request notification for senderId={}",
                acceptConnectionRequestEvent.getSenderId());

        String message = String.format(
                "Your connection request was accepted by user id %d",
                acceptConnectionRequestEvent.getReceiverId()
        );
        sendNotification.send(acceptConnectionRequestEvent.getSenderId(),message);
    }

}
