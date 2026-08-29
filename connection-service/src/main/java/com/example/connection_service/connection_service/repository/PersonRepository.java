package com.example.connection_service.connection_service.repository;

import com.example.connection_service.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person,Long> {

    @Query("""
        MATCH (personA:Person {id: $userId})-[:CONNECTED]->(personB:Person)
        RETURN personB
        """)
    List<Person> getFirstDegreeConnection(Long userId);
}

