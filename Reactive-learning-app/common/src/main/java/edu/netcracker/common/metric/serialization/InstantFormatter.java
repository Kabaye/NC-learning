package edu.netcracker.common.metric.serialization;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.Instant;
import java.util.Locale;

public class InstantFormatter implements Formatter<Instant> {
    @Override
    public Instant parse(String text, Locale locale) throws ParseException {
        return Instant.ofEpochMilli(Long.parseLong(text));
    }

    @Override
    public String print(Instant object, Locale locale) {
        return String.valueOf(object.toEpochMilli());
    }
}