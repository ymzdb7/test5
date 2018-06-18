package com.winhands;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
/**
 * 
 * @项目 oauth 
 * @author yuanl 
 * 2017年7月24日 
 * @Description 
 * 启动类
 */ 
@SpringBootApplication
public class OauthApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(OauthApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	     return application.sources(OauthApplication.class);
	}
	
}
