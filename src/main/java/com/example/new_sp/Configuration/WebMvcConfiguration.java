package com.example.new_sp.Configuration;

import com.example.new_sp.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private CustomerInterceptor customerInterceptor;
    @Autowired
    private SupplierInterceptor supplierInterceptor;
    @Autowired
    private SalespersonInterceptor salespersonInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/static/*")
                .excludePathPatterns("/login.html")
                .excludePathPatterns("/Account")
                .excludePathPatterns("/Account/**");

        registry.addInterceptor(customerInterceptor)
                .addPathPatterns("/main.html")
                .addPathPatterns("/carSelect.html")
                .addPathPatterns("customerMessage.html")
                .addPathPatterns("orderManage.html")
                .addPathPatterns("/Car")
                .addPathPatterns("/Car/*")
                .addPathPatterns("/Orders")
                .addPathPatterns("/Orders/*");
        registry.addInterceptor(salespersonInterceptor)
                 .addPathPatterns("./SalespersonMessage.html");
        registry.addInterceptor(supplierInterceptor)
                .addPathPatterns("/Brands")
                .addPathPatterns("/Car/drop")
                .addPathPatterns("/feedback/content")
                .addPathPatterns("/feedback.html");

    }

}
