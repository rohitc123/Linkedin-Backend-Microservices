package com.example.linkedInApp.posts_service.client;

import com.example.linkedInApp.posts_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service")
public interface ConnectionsClient {
    @GetMapping("/connections/{userId}/first-degree")
    List<PersonDto> getFirstConnections(@PathVariable Long userId);
}
