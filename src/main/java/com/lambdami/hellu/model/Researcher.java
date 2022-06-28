package com.lambdami.hellu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Researcher class holding information for Researchers
 */
@Getter
public class Researcher extends Author {

    @Builder
    public Researcher(List<String> name) {
        super(name);
    }

    public static Researcher from(String author) {
        List<String> names = Arrays.stream(trim(author).split(" "))
                .map(Researcher::trim).collect(Collectors.toList());
        return Researcher.builder().name(names).build();
    }

    private static String trim(String author) {
        author = trimComma(author);
        return trimAnd(author).trim();
    }

    private static String trimAnd(String author) {
        if (author.endsWith(" and")) {
            return author.replace(" and", " ");
        }
        return author;
    }

    private static String trimComma(String author) {
        if (author.endsWith(",")) {
            return author.substring(0, author.length() - 1);
        }
        return author;
    }
}
