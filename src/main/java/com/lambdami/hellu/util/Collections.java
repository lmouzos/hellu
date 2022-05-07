package com.lambdami.hellu.util;

import java.util.Collection;

public class Collections {

    private Collections() {
        // static context
    }
    public static <A, T extends Collection<A>, S extends Collection<A>> boolean any(T col1, S col2) {
        return col1.stream().anyMatch(col2::contains);
    }

    public static <A, T extends Collection<A>, S extends Collection<A>> boolean none(T col1, S col2) {
        return !any(col1, col2);
    }
}
