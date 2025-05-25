package com.spookzie.database.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/*
* Defines how ModelMapper bean will be created & managed by Spring
* ModelMapper - library that automates mapping b/w DTOs & entities
* This class registers the ModelMapper as a reusable, injectable instance that can be accessed using @Autowired or injected
************************************************/
@Configuration
public class MapperConfig
{
    @Bean
    public ModelMapper modelMapper()
    {
        return new ModelMapper();
    }
}