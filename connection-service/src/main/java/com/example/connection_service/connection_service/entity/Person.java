package com.example.connection_service.connection_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Getter
@Setter
public class Person {
    @Id
    private Long id;

    private Long userId;

    private String name;
}
