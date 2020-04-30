package edu.netcracker.common.metrics.converters;

import edu.netcracker.common.metrics.models.MetricType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class WritingMetricsTypeConverter implements Converter<MetricType, String> {
    @Override
    public String convert(MetricType source) {
        return source.name();
    }
}
