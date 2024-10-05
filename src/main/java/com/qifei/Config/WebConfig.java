package com.qifei.Config;

import com.qifei.common.Constants;
import com.qifei.intercepter.Intercepters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
       registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
    }

/*    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(intercepters).addPathPatterns("/**").excludePathPatterns("/")
                .excludePathPatterns("/qifei/**")
                .excludePathPatterns("/common/kaptcha")
                .excludePathPatterns("/common/login");
    }*/
}
