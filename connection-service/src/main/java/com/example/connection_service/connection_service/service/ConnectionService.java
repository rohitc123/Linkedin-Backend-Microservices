package com.example.connection_service.connection_service.service;

import com.example.connection_service.connection_service.auth.UserContextHolder;
import com.example.connection_service.connection_service.entity.Person;
import com.example.connection_service.connection_service.events.AcceptConnectionRequestEvent;
import com.example.connection_service.connection_service.events.SendConnectionRequestEvent;
import com.example.connection_service.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestKafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestKafkaTemplate;


    public List<Person> getFirstConnection(Long userId) {
        log.info("Getting first degree connection of user: "+userId);
        return personRepository.getFirstDegreeConnection(userId);
    }

    public Person createPerson(Person person) {
        log.info("creating person: {}",person.getName());
        return personRepository.save(person);
    }

    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId= UserContextHolder.getCurrentUserId();
        log.info("Sending connection request to sender{},receiver{}",senderId,receiverId);

        if(senderId.equals(receiverId)){
            throw new RuntimeException("Both sender and receiver same");
        }

        boolean requestExist= personRepository.connectionRequestsExists(senderId,receiverId);
        if(requestExist){
            throw new RuntimeException("Connection Request already exists,cannot connect again");
        }

        boolean alreadyConnected= personRepository.alreadyConnected(senderId,receiverId);
        if(alreadyConnected){
            throw new RuntimeException("Already connected users,cannot add connections");
        }
        personRepository.addConnection(senderId,receiverId);
        log.info("Successfully sent the connection request");
        SendConnectionRequestEvent sendConnectionRequestEvent= SendConnectionRequestEvent
                .builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        sendRequestKafkaTemplate.send("send-connection-request-topic",sendConnectionRequestEvent);
        return true;
    }


    public Boolean acceptConnectionRequest(Long senderId) {
        Long receiverId=UserContextHolder.getCurrentUserId();
        log.info("Accepting the connection request");
        boolean requestExist= personRepository.connectionRequestsExists(senderId,receiverId);
        if(!requestExist){
            throw new RuntimeException("no connection exist to accept");
        }
        personRepository.acceptConnection(senderId,receiverId);
        log.info("Successfully accept the connection request");
        AcceptConnectionRequestEvent acceptConnectionRequestEvent= AcceptConnectionRequestEvent
                .builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        acceptRequestKafkaTemplate.send("accept-connection-request-topic",acceptConnectionRequestEvent);
        return true;
    }

    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId=UserContextHolder.getCurrentUserId();
        log.info("Rejecting the connection request");
        boolean requestExist= personRepository.connectionRequestsExists(senderId,receiverId);
        if(!requestExist){
            throw new RuntimeException("Connection request does not exist");
        }
        personRepository.rejectConnection(senderId,receiverId);
        log.info("Request rejected successfully");
        return true;
    }
}
