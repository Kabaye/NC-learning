package utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;
import utils.models.MetricType;

@WritingConverter
@Component
public class WritingMetricsTypeConverter implements Converter<MetricType, String> {
    @Override
    public String convert(MetricType source) {
        return source.name();
    }
}
