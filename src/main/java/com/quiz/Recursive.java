package com.quiz;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Recursive {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(\\d*)(\\[\\w+\\])");
        System.out.println("input : " + "2[abc]3[ab]c");
        System.out.println("result : " + perform(pattern, "2[abc]3[ab]c"));

        System.out.println("input : " + "10[a]c2[ab]");
        System.out.println("result : " + perform(pattern, "10[a]c2[ab]"));

        System.out.println("input : " + "2[3[a]b]");
        System.out.println("result : " + perform(pattern, "2[3[a]b]"));
    }

    private static String perform(Pattern pattern, String input) {
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            input = input.replace(String.valueOf(matcher.group(1) + matcher.group(2)),
                    new String(new char[Integer.parseInt(matcher.group(1))]).replace("\0", (matcher.group(2).replaceAll("[\\[\\]]", "")))); // This way use for java < 10
        }

        return pattern.matcher(input).matches() ? perform(pattern, input) : input;
    }

}