package com.example.linkedInApp.connection_service.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
