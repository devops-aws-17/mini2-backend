package com.mohanit.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info=@Info(title="testtest",version="1.0",description="testtestdesc"))
public class SwggerConfiguration {
	
}
