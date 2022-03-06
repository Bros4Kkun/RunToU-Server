package com.four.brothers.runtou.config;

import com.four.brothers.runtou.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class LoginConfig implements WebMvcConfigurer {

  private final LoginInterceptor loginInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
      .addPathPatterns("/api/**")
      .excludePathPatterns("/api/user/signup/**")
      .excludePathPatterns("/api/user/signin/**");
  }
}
