package com.four.brothers.runtou.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * https://stackoverflow.com/questions/59387737/spring-boot-spring-security-returns-a-status-401-instead-of-404-for-no-mapping
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class NoHandlerFoundInterceptor implements HandlerInterceptor {

  private final DispatcherServlet dispatcherServlet;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String uri = request.getRequestURI();
    if (null == getHandler(request)) {
      response.sendError(HttpServletResponse.SC_NOT_FOUND);
      return false;
    }
    return true;
  }

  protected HandlerExecutionChain getHandler(HttpServletRequest request) {
    if (dispatcherServlet.getHandlerMappings() != null) {
      for (HandlerMapping mapping : dispatcherServlet.getHandlerMappings()) {
        try {
          HandlerExecutionChain handlerExecutionChain = mapping.getHandler(request);
          if (!handlerExecutionChain.getHandler().toString().contains("ResourceHttpRequestHandler")) {
            return handlerExecutionChain;
          }
        } catch (Exception e) {
          //ignore
        }
      }
    }

    return null;
  }

}
