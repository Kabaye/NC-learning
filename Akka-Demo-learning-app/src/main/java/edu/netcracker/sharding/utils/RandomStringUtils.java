package edu.netcracker.sharding.utils;

import edu.netcracker.sharding.language.Languages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * @author svku0919
 * @version 13.08.2020
 */
public class RandomStringUtils {
    private static final List<Character> commonRange;
    private static final Map<Languages, List<Character>> allowedChars;

    static {
        List<Character> localList = new ArrayList<>();
        for (int i = 32; i < 65; i++) {
            localList.add((char) i);
        }
        for (int i = 91; i < 97; i++) {
            localList.add((char) i);
        }
        for (int i = 123; i < 127; i++) {
            localList.add((char) i);
        }

        commonRange = Collections.unmodifiableList(localList);

        // EN
        List<Character> localEnList = new ArrayList<>(commonRange);
        for (int i = 65; i < 91; i++) {
            localEnList.add((char) i);
        }
        for (int i = 97; i < 123; i++) {
            localEnList.add((char) i);
        }

        // RU
        List<Character> localRuList = new ArrayList<>(commonRange);
        for (int i = 1040; i < 1106; i++) {
            localRuList.add((char) i);
        }

        Map<Languages, List<Character>> localMap = new HashMap<>();
        localMap.put(Languages.EN, localEnList);
        localMap.put(Languages.RU, localRuList);
        allowedChars = Collections.unmodifiableMap(localMap);
    }

    public static Stream<Character> chars(Languages lang) {
        return Stream.generate(() -> {
            final List<Character> list = allowedChars.get(lang);
            return list.get(ThreadLocalRandom.current().nextInt(list.size()));
        });
    }
}
