package com.four.brothers.runtou.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.four.brothers.runtou.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@RequiredArgsConstructor
@Component
public class LoginInterceptor implements HandlerInterceptor {
  private final ObjectMapper objectMapper;


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();


    if (session.isNew() || session.getAttribute("loginUser") == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      ErrorDto.Response dto = new ErrorDto.Response("로그인이 필요합니다.");
      String body = objectMapper.writeValueAsString(dto);
      PrintWriter writer = response.getWriter();

      writer.print(body);
      writer.flush();

      return false;
    }

    return true;
  }
}
