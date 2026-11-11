package com.example.connection_service.connection_service.controller;

import com.example.connection_service.connection_service.auth.UserContextHolder;
import com.example.connection_service.connection_service.entity.Person;
import com.example.connection_service.connection_service.service.ConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/connection")
public class ConnectionController {
    private final ConnectionService connectionService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Long userId=UserContextHolder.getCurrentUserId();
        person.setUserId(userId);
        Person savedPerson = connectionService.createPerson(person);
        return ResponseEntity.ok(savedPerson);
    }

    @GetMapping("/{userId}/first-connection")
    public ResponseEntity<List<Person>> getFirstConnection(@PathVariable Long userId){
        return ResponseEntity.ok(connectionService.getFirstConnection(userId));
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionService.sendConnectionRequest(userId));
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionService.acceptConnectionRequest(userId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionService.rejectConnectionRequest(userId));
    }
}
