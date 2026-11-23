package com.yallafood.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import com.yallafood.loadbalancer.RandomLoadBalancerConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@LoadBalancerClient(name = "CANDIDAT", configuration = RandomLoadBalancerConfiguration.class)
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
