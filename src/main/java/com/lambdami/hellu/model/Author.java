package com.lambdami.hellu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author Loukas Mouzos
 * contact: loukas.mouzos@gmail.com
 * site: https://github.com/lmouzos
 * <p>
 * Author class holding basic information for Authors
 */
@Getter
@AllArgsConstructor
public class Author {

    @Deprecated
    String[] name;

    private String firstName;
    private String middleName;
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) && Objects.equals(middleName, author.middleName) && lastName.equals(author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }

    @Deprecated
    public Author(String[] name) {
        this.name = name;
    }

    public String[] getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String toStringBIB() {
        return firstName + ", " + middleName + ", " + lastName;
    }
}
