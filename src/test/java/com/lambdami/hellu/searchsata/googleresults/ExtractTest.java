package com.lambdami.hellu.searchsata.googleresults;

import com.lambdami.hellu.model.Author;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExtractTest {

    @Test
    void getAuthors() {
        Set<String> input = new HashSet<>(Arrays.asList("Loukas Mouzos", "Mouzos, Loukas"));
        Set<Author> result = Extract.getAuthors(input);
        assertEquals(2, result.size());
    }
}