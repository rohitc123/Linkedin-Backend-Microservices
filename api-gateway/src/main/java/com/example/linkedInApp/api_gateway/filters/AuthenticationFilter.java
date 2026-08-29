package com.example.linkedInApp.api_gateway.filters;

import com.example.linkedInApp.api_gateway.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.server.mvc.common.AbstractGatewayDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.HandlerFilterFunction;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter implements HandlerFilterFunction<ServerResponse,ServerResponse> {
    private final JwtService jwtService;

    @Override
    public ServerResponse filter(ServerRequest request, HandlerFunction<ServerResponse> next) throws Exception {
        //Extract the 'Authorization' header from the incoming HTTP request
        String authorizationHeader = request.headers().firstHeader("Authorization");

        //Validate the presence of the header and ensure it follows the "Bearer <token>" format
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).body("Invalid Token Format");
        }

        //Extract the raw JWT string by splitting out the "Bearer " prefix
        String jwtToken = authorizationHeader.split("Bearer ")[1];

        Long userId = jwtService.getUserIdFromToken(jwtToken);

        //mutate the request because serverRequests are immutable
        ServerRequest mutatedRequest = ServerRequest.from(request)
                .header("X-User-Id", userId.toString())
                .build();

        // Forward the mutated request to the next handler in the chain
        return next.handle(mutatedRequest);

    }
}


