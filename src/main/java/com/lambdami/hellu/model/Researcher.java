package com.lambdami.hellu.model;

import lombok.Builder;
import lombok.Getter;

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
    public Researcher(String[] name, String firstName, String middleName, String lastName) {
        super(name, firstName, middleName, lastName);
    }

    public static Researcher from(String author) {
        String auth1 = trimComma(author);
        String auth2 = trimAnd(auth1);

        String[] items = auth2.split(" ");

        if (items.length == 2) {
            if (items[0].contains(",")) {
                return Researcher.builder()
                        .firstName(items[1].trim())
                        .lastName(items[0].replace(",", "").trim())
                        .build();
            }
            return Researcher.builder()
                    .firstName(items[0].trim())
                    .lastName(items[1].trim())
                    .build();
        }

        if (items.length == 3) {
            if (items[0].contains(",")) {
                return Researcher.builder()
                        .firstName(items[1].trim())
                        .lastName(items[0].replace(",", "").trim())
                        .middleName(items[2].trim())
                        .build();
            }
            return Researcher.builder()
                    .firstName(items[0].trim())
                    .middleName(items[1].trim())
                    .lastName(items[2].trim())
                    .build();
        }
        System.err.println("Name cannot be parsed properly, " + author);
        return Researcher.builder().build();
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
