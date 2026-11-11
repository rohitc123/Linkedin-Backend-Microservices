package com.example.linkedInApp.notification_service.client;

import com.example.linkedInApp.notification_service.dto.ApiResponse;
import com.example.linkedInApp.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "connection-service")
public interface ConnectionsClient {
    @GetMapping("/connection/{userId}/first-connection")
    ApiResponse<List<PersonDto>> getFirstConnections(@PathVariable("userId") Long userId);
}
