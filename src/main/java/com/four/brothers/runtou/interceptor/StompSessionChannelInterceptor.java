package com.four.brothers.runtou.interceptor;

import com.four.brothers.runtou.util.JwtFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Component
public class StompSessionChannelInterceptor implements ChannelInterceptor {
  private final JwtFactory jwtFactory;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    log.info("Channel Interceptor");
    MessageHeaders headers = message.getHeaders();
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS, MultiValueMap.class);
    String jwtValue;

    for (Map.Entry e : multiValueMap.entrySet()) {
      log.info("{}: {}", e.getKey(), e.getValue());
    }

    try {
      jwtValue = multiValueMap.get("Authentication").get(0);
    } catch (Exception e) {
      log.info("jwtValue is null");
      return null;
    }

    return message;
  }
}
