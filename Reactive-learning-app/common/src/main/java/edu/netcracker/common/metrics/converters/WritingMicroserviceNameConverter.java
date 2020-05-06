package edu.netcracker.common.metrics.converters;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
import org.springframework.core.convert.converter.Converter;

public class WritingMicroserviceNameConverter implements Converter<MicroserviceName, String> {
    @Override
    public String convert(MicroserviceName source) {
        return source.name();
    }
}
