package com.four.brothers.runtou.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry config) {
    config.enableSimpleBroker("/topic", "/queue"); //메시지 브로커에서 처리된 뒤, 다시 전송될 목적지
    config.setApplicationDestinationPrefixes("/app"); //메시지 브로커로 전달될 메시지의 목적지
    config.setUserDestinationPrefix("/user"); //유저 식별 접두사
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws-stomp").setAllowedOrigins("*").withSockJS(); //프론트에서 sockjs를 사용하는 경우
    registry.addEndpoint("/ws-stomp").setAllowedOrigins("*"); //프론트에서 sockjs를 사용하지 않는 경우
  }

  @Override
  public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
    registration.setMessageSizeLimit(8 * 1024);
  }
}