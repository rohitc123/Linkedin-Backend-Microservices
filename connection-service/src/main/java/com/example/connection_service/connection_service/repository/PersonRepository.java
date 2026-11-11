package com.example.connection_service.connection_service.repository;

import com.example.connection_service.connection_service.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person,Long> {

    @Query("""
        MATCH (p1:Person {userId: $userId})-[:CONNECTED_TO]-(p2:Person)
        RETURN p2
        """)
    List<Person> getFirstDegreeConnection(Long userId);

    @Query("""
            MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person)
            WHERE p1.userId= $senderId AND p2.userId= $receiverId
            RETURN count(r)>0
            """)
    boolean connectionRequestsExists(Long senderId,Long receiverId);

    @Query("""
            MATCH (p1:Person)-[r:CONNECTED_TO]-(p2:Person)
            WHERE p1.userId= $senderId AND p2.userId= $receiverId
            RETURN count(r)>0
            """)
    boolean alreadyConnected(Long senderId,Long receiverId);

    @Query("""
        MATCH (p1:Person {userId: $senderId}),
              (p2:Person {userId: $receiverId})
        CREATE (p1)-[:REQUESTED_TO]->(p2)
        """)
    void addConnection(Long senderId, Long receiverId);


    @Query("""
            MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person)
            WHERE p1.userId= $senderId AND p2.userId= $receiverId
            DELETE r
            CREATE (p1)-[:CONNECTED_TO]->(p2)
            """)
    void acceptConnection(Long senderId,Long receiverId);

    @Query("""
            MATCH (p1:Person)-[r:REQUESTED_TO]->(p2:Person)
            WHERE p1.userId= $senderId AND p2.userId= $receiverId
            DELETE r
            """)
    void rejectConnection(Long senderId,Long receiverId);


}

