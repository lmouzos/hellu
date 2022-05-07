package com.lambdami.hellu.model;

import com.lambdami.hellu.searchsata.googleresults.PublicationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Data model to hold information for Publication
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Publication {
    protected String title;
    protected Set<Author> authors;
    protected String hrefPage;
    protected PublicationType type;
    protected int year;

    public void setYear(int year) {
        this.year = year;
    }

    public String printAuthors() {
        if (authors == null) {
            return "NO AUTHORS FOUND";
        }
        return authors.stream()
                .map(Author::toString)
                .collect(Collectors.joining(","));
    }

    public String printAuthorsBIB() {
        if (authors == null) {
            return "";
        }
        return authors.stream()
                .map(Author::toStringBIB)
                .collect(Collectors.joining(" and "));
    }
}
