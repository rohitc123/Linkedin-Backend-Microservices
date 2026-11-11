package com.example.linkedInApp.api_gateway.config;

import com.example.linkedInApp.api_gateway.filters.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;

    @Bean
    public RouterFunction<ServerResponse> postsServiceRoute() {

        return GatewayRouterFunctions.route("posts-service").
                route(RequestPredicates.path("/posts/**").or(RequestPredicates.path("/likes/**")), HandlerFunctions.http())
                .filter(lb("POSTS-SERVICE"))
                .filter(authenticationFilter)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {

        return GatewayRouterFunctions.route("user-service")
                .route(RequestPredicates.path("/api/v1/users/**"), HandlerFunctions.http())
                .filter(lb("USER-SERVICE"))
                .before(stripPrefix(2))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> connectionServiceRoute() {

        return GatewayRouterFunctions.route("connection-service")
                .route(RequestPredicates.path("/connection/**"),
                        HandlerFunctions.http())
                .filter(lb("CONNECTION-SERVICE"))
                .filter(authenticationFilter)
                .build();
    }



    @Bean
    public RouterFunction<ServerResponse> userSwaggerRoute() {
        return GatewayRouterFunctions.route("user-swagger")
                .route(RequestPredicates.path("/user-service/v3/api-docs"),
                        HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("USER-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> postsSwaggerRoute() {
        return GatewayRouterFunctions.route("posts-swagger")
                .route(RequestPredicates.path("/posts-service/v3/api-docs"),
                        HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("POSTS-SERVICE"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> connectionSwaggerRoute() {
        return GatewayRouterFunctions.route("connection-swagger")
                .route(RequestPredicates.path("/connection-service/v3/api-docs"),
                        HandlerFunctions.http())
                .before(stripPrefix(1))
                .filter(lb("CONNECTION-SERVICE"))
                .build();
    }

}
