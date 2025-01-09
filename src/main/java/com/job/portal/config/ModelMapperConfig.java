package com.job.portal.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Custom converter for String to LocalDate
        modelMapper.addConverter(new Converter<String, LocalDate>() {
            public LocalDate convert(MappingContext<String, LocalDate> context) {
                // Ensure that the date format used is in ISO format or any format you require
                return LocalDate.parse(context.getSource(), DateTimeFormatter.ISO_DATE);
            }
        });
        return new ModelMapper();
    }
}
