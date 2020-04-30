package edu.netcracker.common.metrics.converters;

import edu.netcracker.common.metrics.models.MetricType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadingMetricsTypeConverter implements Converter<String, MetricType> {
    @Override
    public MetricType convert(String source) {
        return MetricType.valueOf(source);
    }
}
