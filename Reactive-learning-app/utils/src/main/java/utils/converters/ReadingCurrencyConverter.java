package utils.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import utils.models.Currency;

@ReadingConverter
@Component
public class ReadingCurrencyConverter implements Converter<String, Currency> {
    @Override
    public Currency convert(String source) {
        return Currency.valueOf(source);
    }
}
