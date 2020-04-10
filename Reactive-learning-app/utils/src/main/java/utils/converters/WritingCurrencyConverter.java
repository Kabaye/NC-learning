package utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;
import utils.models.Currency;

@WritingConverter
@Component
public class WritingCurrencyConverter implements Converter<Currency, String> {
    @Override
    public String convert(Currency source) {
        return source.name();
    }
}
