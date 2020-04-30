package edu.netcracker.common.currency.converters;

import edu.netcracker.common.currency.model.Currency;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class ReadingCurrencyConverter implements Converter<String, Currency> {
    @Override
    public Currency convert(String source) {
        return Currency.valueOf(source);
    }
}
