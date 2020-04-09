package edu.netcracker.customer.app.customer.conversion;

import edu.netcracker.customer.app.customer.entity.Currency;
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
