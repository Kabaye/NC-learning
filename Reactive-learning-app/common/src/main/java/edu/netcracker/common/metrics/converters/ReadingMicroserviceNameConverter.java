package edu.netcracker.common.metrics.converters;

import edu.netcracker.common.metrics.microservice.MicroserviceName;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadingMicroserviceNameConverter implements Converter<String, MicroserviceName> {
    @Override
    public MicroserviceName convert(String source) {
        return MicroserviceName.valueOf(source);
    }
}
