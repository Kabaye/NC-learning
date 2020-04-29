package edu.netcracker.common.metrics.converters;

import edu.netcracker.metrics.models.MetricType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
@Component
public class WritingMetricsTypeConverter implements Converter<MetricType, String> {
    @Override
    public String convert(MetricType source) {
        return source.name();
    }
}
