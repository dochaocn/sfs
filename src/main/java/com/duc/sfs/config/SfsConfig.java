package com.duc.sfs.config;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@MapperScan(basePackages = "com.duc.sfs.mapper")
@ComponentScan(basePackages = {"com.duc.sfs.controller", "com.duc.sfs.service.impl"})
@EnableTransactionManagement
public class SfsConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

    @Bean
    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate(httpsClientRequestFactory());
        // 解决中文乱码问题
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//        converterList.add(converter);
//        restTemplate.setMessageConverters(converterList);
//        return restTemplate;
        return new RestTemplate();
    }

    @Bean
    public HttpsClientRequestFactory httpsClientRequestFactory() {
        return new HttpsClientRequestFactory();
    }
}
