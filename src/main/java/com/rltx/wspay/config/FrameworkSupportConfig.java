package com.rltx.wspay.config;


import com.wl.framework.converter.CustomModelAttributeMethodProcessor;
import com.wl.framework.converter.DateConverter;
import com.wl.framework.converter.RequestDataConvertListArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class FrameworkSupportConfig implements WebMvcConfigurer {
  public void addFormatters(FormatterRegistry formatterRegistry) {
    formatterRegistry.addConverter(new DateConverter());
  }
  
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new RequestDataConvertListArgumentResolver());
    argumentResolvers.add(new CustomModelAttributeMethodProcessor(true));
  }
  
//  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//    converters.add(new CustomDataFastJsonConvertor());
//  }
}
