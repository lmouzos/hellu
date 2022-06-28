package com.lambdami.hellu.model;

import com.lambdami.hellu.processdata.compare.Comparison;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.List;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Author class holding basic information for Authors
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    private static Comparator<Author> COMPARATOR = Comparison::isTheSameAuthor;

    List<String> name;

    @Override
    public String toString() {
        return "Author{" +
                "name=" + name.stream().reduce("", (cur, acc) -> acc + " " + cur) +
                '}';
    }

    public boolean isSameWith(Author other) {
        return Author.COMPARATOR.compare(this, other) == 0;
    }
    /**
     * FIXME
     *
     * @return the name of the Author as a BIB text
     */
    public String toStringBIB() {
        throw new IllegalStateException("Not implemented yet.");
    }
}
