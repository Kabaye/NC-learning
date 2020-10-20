package edu.netcracker.test.logger.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author svku0919
 * @version 19.10.2020
 */
@Service
@Slf4j
public class TestLoggerService {

    public String getRandomString(Integer intAmount) {
        return ThreadLocalRandom.current().ints(Objects.isNull(intAmount) ? 3 : intAmount)
                .collect(() -> new StringJoiner("-"),
                        (stringJoiner, value) -> stringJoiner.add(Integer.toString(value)),
                        StringJoiner::merge).toString();
    }
}
