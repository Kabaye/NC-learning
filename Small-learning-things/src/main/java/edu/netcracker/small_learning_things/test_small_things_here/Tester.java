package edu.netcracker.small_learning_things.test_small_things_here;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {
    private final static String s = """
            40149,Sorry - you are not, authorised to create refund requests.
            40150,Sorry - you are not authorised to export debt escalation requests.
            """;

    public static void main(String[] args) {
//        Pattern pattern = Pattern.compile("\\d+,.*");
        Pattern pattern = Pattern.compile("^(\\d+),(.*)$");
        System.out.println("Error Codes file parsing started");
        s.lines().forEach(line -> {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
//                String[] split = line.split(",");
//                if (split.length > 2) {
//                    split[1] = Arrays.stream(split).skip(2).reduce(split[1], (s1, s2) -> s1 + "," + s2);
//                }
//                System.out.println(split[0] + " ======= " + split[1]);
                System.out.println(matcher.group(1) + " ======= " + matcher.group(2));
            }
        });
    }

}
