package com.lambdami.hellu.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResearcherTest {

    @Test
    void from_SimpleName() {
        String name = "Loukas Mouzos";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(2, names.size());
        assertEquals("Loukas", names.get(0));
        assertEquals("Mouzos", names.get(1));
    }

    @Test
    void from_SimpleNameWithComma() {
        String name = "Mouzos, Loukas";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(2, names.size());
        assertEquals("Mouzos", names.get(0));
        assertEquals("Loukas", names.get(1));
    }

    @Test
    void from_NameWithMiddleName() {
        String name = "Loukas D. Mouzos";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(3, names.size());
        assertEquals("Loukas", names.get(0));
        assertEquals("D.", names.get(1));
        assertEquals("Mouzos", names.get(2));
    }

    @Test
    void from_NameWithMiddleNameAndComma() {
        String name = "Mouzos, Loukas D.";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(3, names.size());
        assertEquals("Mouzos", names.get(0));
        assertEquals("Loukas", names.get(1));
        assertEquals("D.", names.get(2));
    }

    @Test
    void from_NameWithAnd() {
        String name = "Loukas Mouzos and";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(2, names.size());
        assertEquals("Loukas", names.get(0));
        assertEquals("Mouzos", names.get(1));
    }

    @Test
    void from_NameWithTrailingComma() {
        String name = "Loukas Mouzos,";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(2, names.size());
        assertEquals("Loukas", names.get(0));
        assertEquals("Mouzos", names.get(1));
    }

    @Test
    void from_NameWithCommaAndTrailingComma() {
        String name = "Mouzos, Loukas,";
        Researcher researcher = Researcher.from(name);
        List<String> names = researcher.getName();
        assertEquals(2, names.size());
        assertEquals("Mouzos", names.get(0));
        assertEquals("Loukas", names.get(1));
    }
}