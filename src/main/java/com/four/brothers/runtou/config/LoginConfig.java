package com.four.brothers.runtou.config;

import com.four.brothers.runtou.interceptor.AdminLoginInterceptor;
import com.four.brothers.runtou.interceptor.LoginInterceptor;
import com.four.brothers.runtou.interceptor.NoHandlerFoundInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class LoginConfig implements WebMvcConfigurer {

  private final LoginInterceptor loginInterceptor;
  private final AdminLoginInterceptor adminLoginInterceptor;
  private final NoHandlerFoundInterceptor noHandlerFoundInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(noHandlerFoundInterceptor)
      .addPathPatterns("/api/**")
      .excludePathPatterns("/error")
      .order(1);

    registry.addInterceptor(adminLoginInterceptor)
        .addPathPatterns("/admin/**")
        .excludePathPatterns("/admin/db")
        .excludePathPatterns("/admin/db/logout")
        .excludePathPatterns("/error")
        .order(2);

    registry.addInterceptor(loginInterceptor)
      .addPathPatterns("/api/**")
      .excludePathPatterns("/api/user/signup/**")
      .excludePathPatterns("/api/user/signin/**")
      .excludePathPatterns("/api/admin/**")
      .excludePathPatterns("/error")
      .order(3);


  }
}
