package edu.netcracker.common.metric.converter;

import edu.netcracker.common.metric.model.MetricType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadingMetricsTypeConverter implements Converter<String, MetricType> {
    @Override
    public MetricType convert(String source) {
        return MetricType.valueOf(source);
    }
}
