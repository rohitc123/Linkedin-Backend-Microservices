package com.example.linkedInApp.notification_service.consumer;

import com.example.linkedInApp.notification_service.client.ConnectionsClient;
import com.example.linkedInApp.notification_service.dto.ApiResponse;
import com.example.linkedInApp.notification_service.dto.PersonDto;
import com.example.linkedInApp.notification_service.service.SendNotification;
import com.example.linkedInApp.posts_service.event.PostCreatedEvent;
import com.example.linkedInApp.posts_service.event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostsServiceConsumer {

    private final ConnectionsClient connectionsClient;
    private final SendNotification sendNotification;

    @KafkaListener(
            topics = "post-created-topic",
            properties = "spring.json.value.default.type=com.example.linkedInApp.posts_service.event.PostCreatedEvent"
    )
    public void handlePostCreated(PostCreatedEvent postCreatedEvent){
        //get list of connections
        log.info("Sending notification: handlePostCreated:{}",postCreatedEvent);
        ApiResponse<List<PersonDto>> response =
                connectionsClient.getFirstConnections(postCreatedEvent.getCreatorId());
        List<PersonDto> connections = response != null ? response.getData() : null;

        if (connections == null || connections.isEmpty()) {
            log.info("No connections to notify for creatorId={}", postCreatedEvent.getCreatorId());
            return;
        }
        //send notification to first degree connection
        for(PersonDto connection:connections){
            String message = String.format("Your connection created a new post: %s", postCreatedEvent.getContent());
            sendNotification.send(connection.getUserId(), message);
        }
    }

    @KafkaListener(
            topics = "post-liked-topic",
            properties = "spring.json.value.default.type=com.example.linkedInApp.posts_service.event.PostLikedEvent")
    public void handlePostLiked(PostLikedEvent postLikedEvent){
        log.info("Sending notification: handlePostLiked:{}",postLikedEvent);
        String message = String.format(
                "Your post %d was liked by user %d", postLikedEvent.getPostId(),
                postLikedEvent.getLikedByUserId()
        );

        postLikedEvent.getLikedByUserId();
        sendNotification.send(postLikedEvent.getCreatorId(),message);
    }


}
