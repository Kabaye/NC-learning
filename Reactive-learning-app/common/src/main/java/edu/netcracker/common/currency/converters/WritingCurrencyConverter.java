package edu.netcracker.common.currency.converters;

import edu.netcracker.common.currency.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class WritingCurrencyConverter implements Converter<Currency, String> {
    @Override
    public String convert(Currency source) {
        return source.name();
    }
}
