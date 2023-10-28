package hello.advanced.config;

import hello.advanced.app.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppV1Config {
    @Bean
    public OrderControllerV1 orderControllerV1(){
        return new OrderControllerV1Impl(orderServiceV1());
    }

    public OrderServiceV1 orderServiceV1() {
        return new OrderServiceV1Impl(orderRepository());
    }

    public OrderRepositoryV1 orderRepository() {
        return new OrderRepositoryV1Impl();
    }
}
