package utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import utils.models.MetricType;

@ReadingConverter
@Component
public class ReadingMetricsTypeConverter implements Converter<String, MetricType> {
    @Override
    public MetricType convert(String source) {
        return MetricType.valueOf(source);
    }
}
