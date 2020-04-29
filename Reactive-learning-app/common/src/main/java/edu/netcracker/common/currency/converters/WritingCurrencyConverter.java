package edu.netcracker.common.currency.converters;

import edu.netcracker.common.currency.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@WritingConverter
@Component
public class WritingCurrencyConverter implements Converter<Currency, String> {
    @Override
    public String convert(Currency source) {
        return source.name();
    }
}
