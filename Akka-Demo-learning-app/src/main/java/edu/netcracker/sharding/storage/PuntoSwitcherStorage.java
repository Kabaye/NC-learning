package edu.netcracker.sharding.storage;

import edu.netcracker.sharding.language.LanguagesPairs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static edu.netcracker.sharding.language.LanguagesPairs.EN_RU;
import static edu.netcracker.sharding.language.LanguagesPairs.RU_EN;

/**
 * @author svku0919
 * @version 12.08.2020
 */
public final class PuntoSwitcherStorage {
    private final static Map<LanguagesPairs, Map<Character, Character>> puntoSwitcherStorage;

    static {
        /*
         * Initialize language specific maps here
         * */
        Map<Character, Character> localMapEnRu = new HashMap<>();

        {
            localMapEnRu.put('q', 'й');
            localMapEnRu.put('w', 'ц');
            localMapEnRu.put('e', 'у');
            localMapEnRu.put('r', 'к');
            localMapEnRu.put('t', 'е');
            localMapEnRu.put('y', 'н');
            localMapEnRu.put('u', 'г');
            localMapEnRu.put('i', 'ш');
            localMapEnRu.put('o', 'щ');
            localMapEnRu.put('p', 'з');
            localMapEnRu.put('[', 'х');
            localMapEnRu.put(']', 'ъ');
            localMapEnRu.put('a', 'ф');
            localMapEnRu.put('s', 'ы');
            localMapEnRu.put('d', 'в');
            localMapEnRu.put('f', 'а');
            localMapEnRu.put('g', 'п');
            localMapEnRu.put('h', 'р');
            localMapEnRu.put('j', 'о');
            localMapEnRu.put('k', 'л');
            localMapEnRu.put('l', 'д');
            localMapEnRu.put(';', 'ж');
            localMapEnRu.put('\'', 'э');
            localMapEnRu.put('z', 'я');
            localMapEnRu.put('x', 'ч');
            localMapEnRu.put('c', 'с');
            localMapEnRu.put('v', 'м');
            localMapEnRu.put('b', 'и');
            localMapEnRu.put('n', 'т');
            localMapEnRu.put('m', 'ь');
            localMapEnRu.put(',', 'б');
            localMapEnRu.put('.', 'ю');
            localMapEnRu.put('`', 'ё');

            localMapEnRu.put('Q', 'Й');
            localMapEnRu.put('W', 'Ц');
            localMapEnRu.put('E', 'У');
            localMapEnRu.put('R', 'К');
            localMapEnRu.put('T', 'Е');
            localMapEnRu.put('Y', 'Н');
            localMapEnRu.put('U', 'Г');
            localMapEnRu.put('I', 'Ш');
            localMapEnRu.put('O', 'Щ');
            localMapEnRu.put('P', 'З');
            localMapEnRu.put('{', 'Х');
            localMapEnRu.put('}', 'Ъ');
            localMapEnRu.put('A', 'Ф');
            localMapEnRu.put('S', 'Ы');
            localMapEnRu.put('D', 'В');
            localMapEnRu.put('F', 'А');
            localMapEnRu.put('G', 'П');
            localMapEnRu.put('H', 'Р');
            localMapEnRu.put('J', 'О');
            localMapEnRu.put('K', 'Л');
            localMapEnRu.put('L', 'Д');
            localMapEnRu.put(':', 'Ж');
            localMapEnRu.put('\"', 'Э');
            localMapEnRu.put('Z', 'Я');
            localMapEnRu.put('X', 'Ч');
            localMapEnRu.put('C', 'С');
            localMapEnRu.put('V', 'М');
            localMapEnRu.put('B', 'И');
            localMapEnRu.put('N', 'Т');
            localMapEnRu.put('M', 'Ь');
            localMapEnRu.put('<', 'Б');
            localMapEnRu.put('>', 'Ю');
            localMapEnRu.put('~', 'Ё');
        }

        /*
         * Initialize global storage map here
         */

        Map<LanguagesPairs, Map<Character, Character>> localStorage = new HashMap<>();
        localStorage.put(EN_RU, localMapEnRu);
        localStorage.put(RU_EN, reverseMap(localMapEnRu));

        puntoSwitcherStorage = Collections.unmodifiableMap(localStorage);
    }

    public static Character getConvertedSymbol(char symbol, LanguagesPairs langPair) {
        return puntoSwitcherStorage.get(langPair).get(symbol);
    }

    private static <T, U> Map<U, T> reverseMap(Map<T, U> mapToReverse) {
        return mapToReverse.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }
}
