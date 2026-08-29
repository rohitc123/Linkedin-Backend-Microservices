package com.example.connection_service.connection_service.service;

import com.example.connection_service.connection_service.entity.Person;
import com.example.connection_service.connection_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionService {

    private final PersonRepository personRepository;

    public List<Person> getFirstConnection(Long userId) {
        log.info("Getting first degree connection of user: "+userId);
        return personRepository.getFirstDegreeConnection(userId);
    }

    public Person createPerson(Person person) {
        log.info("creating person: {}",person.getName());
        return personRepository.save(person);
    }
}
